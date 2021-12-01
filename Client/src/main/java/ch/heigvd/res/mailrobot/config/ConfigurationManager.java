package ch.heigvd.res.mailrobot.config;

import ch.heigvd.res.mailrobot.model.mail.Person;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
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
    private Person witnessesToCc;
    private final Properties prop;
    private int numberOfGroup;

    public String getSmtpServerAddress() {
        return smtpServerAddress;
    }

    public int getSmtpServerPort() {
        return smtpServerPort;
    }

    public List<Person> getVictims() {
        return victims;
    }

    public List<String> getMessages() {
        return messages;
    }

    public Person getWitnessesToCc() {
        return witnessesToCc;
    }

    public int getNumberOfGroup() {
        return numberOfGroup;
    }

    private void storeMessages(){
        BufferedReader messagesFile = null;

        try{
            String line;
            StringBuilder message = new StringBuilder();
            messagesFile = new BufferedReader(new FileReader("src/main/ressources/messages.utf8"));
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

    private void storeVictims() {
        BufferedReader victimsFile = null;

        try{
            victimsFile = new BufferedReader(new FileReader("src/main/ressources/victims.utf8"));

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

    private InputStream getRessourceAsStream() throws IOException {
        InputStream in = ConfigurationManager.class.getResourceAsStream("/config.properties");
        if (in == null)
            throw new IOException("File " + "/config.properties" + " not found.");

        return in;
    }

    private void storeConfiguration(){
        InputStream config = null;
        try{

            config = getRessourceAsStream();

            prop.load(config);

            smtpServerAddress = prop.getProperty("smtpServerAddress");
            numberOfGroup = Integer.parseInt(prop.getProperty("numberOfGroups"));
            smtpServerPort = Integer.parseInt(prop.getProperty("smtpServerPort"));

            String s = prop.getProperty("witnessesToCC");
            String[] dotSeparation = s.split("\\.");
            String atSeparation = dotSeparation[1].split("\\@")[0];
            witnessesToCc = new Person(dotSeparation[0], atSeparation, s);
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

    public ConfigurationManager() {
        prop = new Properties();

        storeVictims();
        storeMessages();
        storeConfiguration();
    }
}
