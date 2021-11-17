package ch.heigvd.res.mailrobot.smtp;

import ch.heigvd.res.mailrobot.config.ConfigurationManager;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SmtpClient {
    private final static Logger LOG = Logger.getLogger(SmtpClient.class.getName());
    private static int port;
    private static String host;
    ConfigurationManager config = new ConfigurationManager();

    public SmtpClient(){
        port = config.getSmtpServerPort();
        host = config.getSmtpServerAddress();

        System.setProperty("java.util.logging.SimpleFormatter.format", "%4$s: %5$s%6$s%n");

        Socket clientSocket = null;
        BufferedReader in = null;
        BufferedWriter out = null;
        BufferedReader usrin = null;

        try {
            clientSocket = new Socket(host, port);

            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), StandardCharsets.UTF_8));
            out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream(), StandardCharsets.UTF_8));
            usrin = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));

            LOG.log(Level.INFO, "Connected to" + clientSocket);

            String line = "ThIS is a TeSt", response;

            while ((response = in.readLine()) != null) {
                System.out.println("Server : " + response);

                if (response.equals(line.toUpperCase())) {
                    LOG.log(Level.INFO, "Answer from server is correct");
                    break;
                }

                out.write(line + '\n');
                out.flush();
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.toString(), e);
        } finally {
            try {
                if (usrin != null)
                    usrin.close();
            } catch (IOException e) {
                LOG.log(Level.SEVERE, e.toString(), e);
            }
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
    }
}
