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
    ConfigurationManager config = new ConfigurationManager();

    public SmtpClient(List<Prank> pranks) {
        this.pranks = pranks;
    }

    public boolean checkResponse(String response, String attendu){
        return response == null || !response.startsWith(attendu);
    }

    public int start(){
        String response;
        int port = config.getSmtpServerPort();
        String host = config.getSmtpServerAddress();

        System.setProperty("java.util.logging.SimpleFormatter.format", "%4$s: %5$s%6$s%n");

        Socket clientSocket = null;
        BufferedReader in = null;
        BufferedWriter out = null;

        try {
            clientSocket = new Socket(host, port);

            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), StandardCharsets.UTF_8));
            out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream(), StandardCharsets.UTF_8));

            LOG.log(Level.INFO, "Connected to" + clientSocket);

            if (checkResponse(response = in.readLine(), "250")) {
                LOG.log(Level.SEVERE, "Réponse attendue : 250-..., recue " + response);
                return -1;
            }

            for (Prank p : pranks) {
                out.write("EHLO heig-vd.com" + '\n');
                out.flush();

                while ((response = in.readLine()) != null && !response.startsWith("250 ")) {
                    if (checkResponse(response, "250")) {
                        LOG.log(Level.SEVERE, "Réponse attendue : 250-..., recue " + response);
                        return -1;
                    }
                    System.out.println(response);
                }
                System.out.println(response);

                out.write("MAIL FROM:<" + p.getSender().getAddress() + ">\n");
                out.flush();
                for (Person pers : p.getVictims()) {
                    out.write("RCPT TO:<" + pers.getAddress() + ">\n");
                    out.flush();

                    if (checkResponse(response = in.readLine(), "250 ")) {
                        LOG.log(Level.SEVERE, "Réponse attendue : 250 OK, recue " + response);
                        return -1;
                    }
                    System.out.println(response);
                }

                out.write("DATA\n");
                out.flush();

                if(checkResponse(response = in.readLine(), "354 ")){
                    LOG.log(Level.SEVERE, "Réponse attendue : 354 , recue " + response);
                    return -1;
                }

                System.out.println(response);

                out.write(writeContent(p));
                out.flush();

                if(checkResponse(response = in.readLine(), "250 ")){
                    LOG.log(Level.SEVERE, "Réponse attendue : 250 , recue " + response);
                    return -1;
                }

                System.out.println(response);
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
        toReturn.append("From: ").append(p.getSender().getAddress()).append("\n");

        for(Person pers : p.getVictims()){
            toReturn.append("To: ").append(pers.getFirstname()).append(" ")
                    .append(pers.getLastname()).append(" <").append(pers.getAddress()).append(">\n");
        }
        toReturn.append("Cc: ").append(p.getWitness().getFirstname()).append(" ")
                .append(p.getWitness().getLastname()).append(" <").append(p.getWitness().getAddress()).append(">\n");

        toReturn.append(p.getMessage());

        toReturn.append("\r\n.\r\n");
        return toReturn.toString();
    }
}
