package ch.heigvd.res.mailrobot.model.prank;

import ch.heigvd.res.mailrobot.model.mail.Person;

import java.util.List;

public class Prank {
    private final Person sender;
    private final List<Person> victims;
    private final Person witness;
    private final String body;
    private final String subject;

    public Person getSender() {
        return sender;
    }

    public List<Person> getVictims() {
        return victims;
    }

    public Person getWitness() {
        return witness;
    }

    public String getBody() {
        return body;
    }

    public Prank(List<Person> victims, Person witness, String message, String subject){
        sender = victims.get(0);
        this.body = message;
        this.victims = victims.subList(1, victims.size());
        this.witness = witness;
        this.subject = subject;
    }

    public String getSubject() {return subject;}
}
