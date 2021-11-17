package ch.heigvd.res.mailrobot.config;

import ch.heigvd.res.mailrobot.model.mail.Person;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class ConfigurationManager {
    private static final Logger LOG = Logger.getLogger(FileHandler.class.getName());

    private String smtpServerAddress;
    private int smtpServerPort;
    private final List<Person> victims = new ArrayList<>();
    private final List<String> messages = new ArrayList<>();
    private List<Person> witnessesToCc;
    private int numberOfGroup;
    private int groupNum = 0;
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

    public ConfigurationManager() throws IOException {
        BufferedReader victimsFile = null;
        BufferedReader messagesFile = null;
        Vector<Person> victims = new Vector<>();
        Vector<String> messages = new Vector<>();


        victimsFile = new BufferedReader(new FileReader("../ressources/victims.utf8"));
        messagesFile = new BufferedReader(new FileReader("../ressources/messages.utf8"));

        String line;
        Person p;
        while((line = victimsFile.readLine()) != null){
            // TODO prenom.nom@...
            p = new Person("", "", line + "\r\n");
            victims.add(p);
        }
        while((line = messagesFile.readLine()) != null){
            messages.add(line + "\r\n");
        }

        victimsFile.close();
        messagesFile.close();
    }
}
