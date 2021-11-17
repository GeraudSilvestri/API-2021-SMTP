package ch.heigvd.res.mailrobot.model.prank;

import ch.heigvd.res.mailrobot.config.ConfigurationManager;
import ch.heigvd.res.mailrobot.model.mail.Group;
import ch.heigvd.res.mailrobot.model.mail.Person;

import java.util.ArrayList;
import java.util.List;

public class PrankGenerator {
    private List<Group> groups = new ArrayList<>();
    private final ConfigurationManager config = new ConfigurationManager();

    private void generateGroups(){
        List<Person> victims = config.getVictims();
    }
}
