package ch.heigvd.res.mailrobot.smtp;

import ch.heigvd.res.mailrobot.config.ConfigurationManager;
import ch.heigvd.res.mailrobot.model.mail.Person;
import ch.heigvd.res.mailrobot.model.prank.Prank;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SmtpClient {
    private final static Logger LOG = Logger.getLogger(SmtpClient.class.getName());
    private final List<Prank> pranks;
    private final String CRLF = "\r\n";
    private BufferedReader in = null;
    private BufferedWriter out = null;
    private final ConfigurationManager config;
    private String answer;

    public SmtpClient(List<Prank> pranks, ConfigurationManager conf) {
        this.pranks = pranks;
        config = conf;
    }

    public boolean checkResponse(String attendu) throws IOException {
        answer = in.readLine();
        return answer == null || !answer.startsWith(attendu);
    }

    public int start(){
        int port = config.getSmtpServerPort();
        String host = config.getSmtpServerAddress();

        System.setProperty("java.util.logging.SimpleFormatter.format", "%4$s: %5$s%6$s%n");

        Socket clientSocket = null;

        try {
            clientSocket = new Socket(host, port);

            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), StandardCharsets.UTF_8));
            out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream(), StandardCharsets.UTF_8));

            LOG.log(Level.INFO, "Connected to" + clientSocket);

            if (checkResponse("220 ")) {
                LOG.log(Level.SEVERE, "Réponse attendue : 220 , recue " + answer);
                return -1;
            }
            System.out.println(answer);

            for (Prank p : pranks) {
                send("EHLO heig-vd.com" + CRLF);

                while ((answer = in.readLine()) != null) {
                    if(answer.startsWith("250 ")){
                        break;
                    }
                    if (checkResponse("250")) {
                        LOG.log(Level.SEVERE, "Réponse attendue : 250-..., recue " + answer);
                        return -1;
                    }
                    System.out.println(answer);
                }
                System.out.println(answer);

                send("MAIL FROM:<" + p.getSender().getAddress() + ">" + CRLF);
                if(checkResponse("250 ")){
                    LOG.log(Level.SEVERE, "Réponse attendue : 250 OK , recue " + answer);
                    return -1;
                }
                for (Person pers : p.getVictims()) {
                    send("RCPT TO:<" + pers.getAddress() + ">"+ CRLF);

                    if (checkResponse("250 ")) {
                        LOG.log(Level.SEVERE, "Réponse attendue : 250 OK, recue " + answer);
                        return -1;
                    }
                    System.out.println(answer);
                }

                send("DATA"+CRLF);

                if(checkResponse("354 ")){
                    LOG.log(Level.SEVERE, "Réponse attendue : 354 , recue " + answer);
                    return -1;
                }

                System.out.println(answer);

                send(writeContent(p));

                if(checkResponse("250 ")){
                    LOG.log(Level.SEVERE, "Réponse attendue : 250 , recue " + answer);
                    return -1;
                }

                System.out.println(answer);
            }

        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.toString(), e);
        }
        finally {
            try {
                if (in != null)
                    in.close();
            } catch (IOException e) {
                LOG.log(Level.SEVERE, e.toString(), e);
            }
            try {
                if (out != null)
                    out.close();
            } catch (IOException e) {
                LOG.log(Level.SEVERE, e.toString(), e);
            }
            try {
                if (clientSocket != null && !clientSocket.isClosed())
                    clientSocket.close();
            } catch (IOException e) {
                LOG.log(Level.SEVERE, e.toString(), e);
            }
        }
        return 0;
    }

    /**
     * génère le contenu du message en fonction du sender, des victimes et du witness
     * @param p prank contenant toutes les données
     * @return contenu du mail
     */
    private String writeContent(Prank p){
        StringBuilder toReturn = new StringBuilder();
        toReturn.append("From: ").append(p.getSender().getAddress()).append(CRLF);

        for(Person pers : p.getVictims()){
            toReturn.append("To: ").append(pers.getFirstname()).append(" ")
                    .append(pers.getLastname()).append(" <").append(pers.getAddress()).append(">").append(CRLF);
        }
        toReturn.append("Cc: ").append(p.getWitness().getFirstname()).append(" ")
                .append(p.getWitness().getLastname()).append(" <").append(p.getWitness().getAddress()).append(">").append(CRLF);

        toReturn.append(p.getMessage());

        toReturn.append(CRLF + "." + CRLF);
        return toReturn.toString();
    }

    private void send(String message) throws IOException {
        out.write(message);
        out.flush();
        System.out.println(message);
    }
}
