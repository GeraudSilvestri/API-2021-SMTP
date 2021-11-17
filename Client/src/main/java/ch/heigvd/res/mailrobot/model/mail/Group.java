package ch.heigvd.res.mailrobot.model.mail;

import java.util.ArrayList;
import java.util.List;

public class Group {
    private final List<Person> members = new ArrayList<>();

    public void addMember(Person p){
        members.add(p);
    }

    public List<Person> getMembers() {
        return members;
    }
}
