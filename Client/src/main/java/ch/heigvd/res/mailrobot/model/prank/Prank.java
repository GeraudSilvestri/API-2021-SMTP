package ch.heigvd.res.mailrobot.model.prank;

import ch.heigvd.res.mailrobot.model.mail.Person;

import java.util.List;

public class Prank {
    private final Person sender;
    private final List<Person> victims;
    private final Person witness;
    private final String message;

    public Person getSender() {
        return sender;
    }

    public List<Person> getVictims() {
        return victims;
    }

    public Person getWitness() {
        return witness;
    }

    public String getMessage() {
        return message;
    }

    public Prank(List<Person> victims, Person witness, String message){
        sender = victims.get(0);
        this.message = message;
        this.victims = victims.subList(1, victims.size());
        this.witness = witness;
    }
}
