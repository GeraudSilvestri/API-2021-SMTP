import java.io.*;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.net.Socket;

/**
 * Calculator client implementation
 */
public class FileHandler {

    private static final Logger LOG = Logger.getLogger(FileHandler.class.getName());

    /**
     * Main function to run client
     *
     * @param args no args required
     */

    public static void main(String[] args) throws IOException {
        BufferedReader victimsFile = null;
        BufferedWriter messagesFile = null;
        Vector<String> victims = new Vector<>();

        int numberGroup = Integer.parseInt(args[0]);
        try{
            victimsFile = new BufferedReader(new FileReader("victims"));
            messagesFile = new BufferedWriter(new FileWriter("messages"));

            String line;

            while((line = victimsFile.readLine()) != null){
                victims.add(line);
            }
            int x = 0;
        }catch(IOException e){

        }
        finally{
            try{
                if(victimsFile != null)
                    victimsFile.close();
                if(messagesFile != null)
                    messagesFile.close();
            }
            catch(IOException e){

            }
        }
    }
}
