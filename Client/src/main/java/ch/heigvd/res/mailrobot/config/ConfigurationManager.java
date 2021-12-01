package ch.heigvd.res.mailrobot.config;

import ch.heigvd.res.mailrobot.model.mail.Person;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConfigurationManager {
    private static final Logger LOG = Logger.getLogger(FileHandler.class.getName());
    private String smtpServerAddress;
    private int smtpServerPort;
    private final List<Person> victims = new ArrayList<>();
    private final List<String> messages = new ArrayList<>();
    private List<Person> witnessesToCc = new ArrayList<>();;
    private Properties prop;
    private int numberOfGroup;
    private final int GROUP_MIN_SIZE = 3;

    public String getSmtpServerAddress() {
        return smtpServerAddress;
    }

    public void setSmtpServerAddress(String smtpServerAddress) {
        this.smtpServerAddress = smtpServerAddress;
    }

    public int getSmtpServerPort() {
        return smtpServerPort;
    }

    public void setSmtpServerPort(int smtpServerPort) {
        this.smtpServerPort = smtpServerPort;
    }

    public List<Person> getVictims() {
        return victims;
    }

    public List<String> getMessages() {
        return messages;
    }

    public List<Person> getWitnessesToCc() {
        return witnessesToCc;
    }

    public void setWitnessesToCc(List<Person> witnessesToCc) {
        this.witnessesToCc = witnessesToCc;
    }

    public int getNumberOfGroup() {
        return numberOfGroup;
    }

    private void storeMessages(String filename){
        BufferedReader messagesFile = null;

        try{
            String line;
            StringBuilder message = new StringBuilder();
            messagesFile = new BufferedReader(new FileReader(filename));
            while((line = messagesFile.readLine()) != null){
                if(line.equals("==")){
                    messages.add(message.toString());
                    message.setLength(0);
                }else{
                    message.append(line).append("\r\n");
                }
            }

            if(message.length() != 0){
                messages.add(message.toString());
            }
        }
        catch(IOException e){
            LOG.log(Level.SEVERE, e.toString());
        }
        finally{
            try{
                if(messagesFile != null)
                    messagesFile.close();
            }
            catch(IOException e){
                LOG.log(Level.SEVERE, e.toString());
            }
        }
    }

    private void storeVictims(String filename) {
        BufferedReader victimsFile = null;

        try{
            victimsFile = new BufferedReader(new FileReader(filename));

            String line;
            while((line = victimsFile.readLine()) != null){
                String[] dotSeparation = line.split("\\.");
                String atSeparation = dotSeparation[1].split("\\@")[0];
                victims.add(new Person(dotSeparation[0], atSeparation, line + "\r\n"));
            }
        }
        catch(IOException e){
            LOG.log(Level.SEVERE, e.toString());
        }
        finally{
            try{
                if(victimsFile != null)
                    victimsFile.close();
            }
            catch(IOException e){
                LOG.log(Level.SEVERE, e.toString());
            }
        }
    }

    private void countGroups(int numberLine){
    }

    private InputStream getRessourceAsStream(String fileName) throws IOException {
        InputStream in = ConfigurationManager.class.getResourceAsStream(fileName);
        if (in == null)
            throw new IOException("File " + fileName + " not found.");

        return in;
    }

    private void storeConfiguration(String filename){
        InputStream config = null;
        try{

            config = getRessourceAsStream(filename);

            prop.load(config);

            smtpServerAddress = prop.getProperty("smtpServerAddress");
            numberOfGroup = Integer.parseInt(prop.getProperty("numberOfGroups"));
            smtpServerPort = Integer.parseInt(prop.getProperty("smtpServerPort"));

            for(String s : prop.getProperty("witnessesToCC").split("\\,")){
                String[] dotSeparation = s.split("\\.");
                String atSeparation = dotSeparation[1].split("\\@")[0];
                witnessesToCc.add(new Person(dotSeparation[0], atSeparation, s + "\r\n"));
            }
        }
        catch(IOException e){
            LOG.log(Level.SEVERE, e.toString());
        }
        finally{
            try{
                if(config != null)
                    config.close();
            }
            catch(IOException e){
                LOG.log(Level.SEVERE, e.toString());
            }
        }
    }

    public ConfigurationManager() throws IOException {
        prop = new Properties();

        storeVictims("src/main/ressources/victims.utf8");
        storeMessages("src/main/ressources/messages.utf8");
        storeConfiguration("/config.properties");
    }
}
