package ch.heigvd.res.mailrobot.config;

import java.io.*;
import java.util.Random;
import java.util.Vector;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class ConfigurationManager {
    private static final Logger LOG = Logger.getLogger(FileHandler.class.getName());

    private String smtpServerAddress;
    private int smtpServerPOrt;
    private final list<Person> victims;
    private final list<String> messages;
    private list<Person> witnessesToCc;

    public String getSmtpServerAddress() {
        return smtpServerAddress;
    }

    public void setSmtpServerAddress(String smtpServerAddress) {
        this.smtpServerAddress = smtpServerAddress;
    }

    public int getSmtpServerPOrt() {
        return smtpServerPOrt;
    }

    public void setSmtpServerPOrt(int smtpServerPOrt) {
        this.smtpServerPOrt = smtpServerPOrt;
    }

    public list<Person> getVictims() {
        return victims;
    }

    public list<String> getMessages() {
        return messages;
    }

    public list<Person> getWitnessesToCc() {
        return witnessesToCc;
    }

    public void setWitnessesToCc(list<Person> witnessesToCc) {
        this.witnessesToCc = witnessesToCc;
    }

    /*
    private void createGroupe(int groupNum, Vector<String> victims){
        if(victims.size() < groupNum * 3 || groupNum <= 0){
            return;
        }else{
            Random rand = new Random();
        }
    }*/

    /**
     * Main function to run client
     *
     * @param args no args required
     */

    public static void main(String[] args) throws IOException {
        /*BufferedReader victimsFile = null;
        BufferedReader messagesFile = null;
        Vector<String> victims = new Vector<>();
        Vector<String> messages = new Vector<>();

        int numberGroup = Integer.parseInt(args[0]);
        try{
            victimsFile = new BufferedReader(new FileReader("config/victims.utf8"));
            messagesFile = new BufferedReader(new FileReader("config/messages.utf8"));

            String line;
            while((line = victimsFile.readLine()) != null){
                victims.add(line + "\r\n");
            }
            while((line = messagesFile.readLine()) != null){
                messages.add(line + "\r\n");
            }

        }catch(IOException e){
            System.out.println("Erreur survenue lors de la lecture des fichiers victims et messages");
        }
        finally{
            try{
                if(victimsFile != null)
                    victimsFile.close();
                if(messagesFile != null)
                    messagesFile.close();
            }
            catch(IOException e){
                System.out.println("Erreur survenue lors de la fermeture des fichiers victims et messages");
            }
        }*/
    }
}
