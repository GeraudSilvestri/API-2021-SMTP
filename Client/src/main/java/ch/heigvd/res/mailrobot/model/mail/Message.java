package ch.heigvd.res.mailrobot.model.mail;

public class Message {
    private String from;
    private String[] to = new String[0];
    private String[] cc = new String[0];
    private String[] bcc = new String[0];
    private String subject;
    private String body;
}
