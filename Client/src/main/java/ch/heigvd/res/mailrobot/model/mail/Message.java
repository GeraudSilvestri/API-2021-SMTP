package ch.heigvd.res.mailrobot.model.mail;

public class Message {
    private String from;
    private String[] to = new String[0];
    private String[] cc = new String[0];
    private String[] bcc = new String[0];
    private String subject;
    private String body;

    public Message(String from, String[] to, String[] cc, String[] bcc, String subject, String body) {
        this.from = from;
        this.to = to;
        this.cc = cc;
        this.bcc = bcc;
        this.subject = subject;
        this.body = body;
    }

    public String getFrom() {
        return from;
    }

    public String[] getTo() {
        return to;
    }

    public String[] getCc() {
        return cc;
    }

    public String[] getBcc() {
        return bcc;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }
}
