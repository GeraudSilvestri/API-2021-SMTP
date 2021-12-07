package ch.heigvd.res.mailrobot.model.mail;

import java.util.ArrayList;
import java.util.List;

public class Group {
    private final List<Person> members;

    public Group(List<Person> m){
        members = m;
    }

    public List<Person> getMembers() {
        return members;
    }
}
