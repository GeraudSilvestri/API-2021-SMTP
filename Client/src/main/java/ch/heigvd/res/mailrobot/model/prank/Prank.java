package ch.heigvd.res.mailrobot.model.prank;

import ch.heigvd.res.mailrobot.model.mail.Person;

import java.util.ArrayList;
import java.util.List;

public class Prank {
    private Person sender;
    private List<Person> victims = new ArrayList<>();
    private List<Person> witness = new ArrayList<>();
    private String message;
}
