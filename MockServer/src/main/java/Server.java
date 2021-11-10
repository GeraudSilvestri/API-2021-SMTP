import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Calculator server implementation - single threaded
 */
public class Server {

    private final static Logger LOG = Logger.getLogger(Server.class.getName());
    final int PORT = 4269;
    final String CRLF = "\r\n";

    BufferedReader in = null;
    BufferedWriter out = null;
    ServerSocket serverSocket;
    Socket clientSocket = null;

    /**
     * Main function to start the server
     */
    public static void main(String[] args) {
        // Log output on a single line
        System.setProperty("java.util.logging.SimpleFormatter.format", "%4$s: %5$s%6$s%n");

        (new Server()).start();
    }

    /**
     * Start the server on a listening socket.
     */
    private void start() {
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
            return;
        }
        while (true) {
            try {
                LOG.log(Level.INFO, "Single-threaded: Waiting for a new client on port {0}", PORT);
                clientSocket = serverSocket.accept();

                handleClient(clientSocket);
                String line;
            }
            catch(Exception ex){
                try{
                    exit();
                }catch(IOException e){
                    LOG.log(Level.SEVERE, e.getMessage(), e);
                }
                LOG.log(Level.SEVERE, ex.getMessage(), ex);
            }
        }
    }

    /**
     * Handle a single client connection: receive commands and send back the result.
     *
     * @param clientSocket with the connection with the individual client.
     */
    private void handleClient(Socket clientSocket) throws IOException {
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), "UTF-8"));
        out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream(), "UTF-8"));

        out.write("connexion etablie..." + CRLF);
        out.flush();

        String line;
        while ( (line = in.readLine()) != null ) {
            if (line.equalsIgnoreCase("END")) {
                break;
            }

            switch(line.substring(0,3)){

                default:
                    out.write("COE : command error" + CRLF);
                    out.flush();
                    break;
            }
        }
    }

    /**
     * commits hara-kiri
     */
    private void exit() throws IOException{
        if (in != null) {
            try {
                in.close();
            } catch (IOException ex1) {
                throw ex1;
            }
        }
        if (out != null) {
            try {
                out.close();
            } catch (IOException ex1) {
                throw ex1;
            }
        }
        if (clientSocket != null) {
            try {
                clientSocket.close();
            } catch (IOException ex1) {
                throw ex1;
            }
        }
    }
}