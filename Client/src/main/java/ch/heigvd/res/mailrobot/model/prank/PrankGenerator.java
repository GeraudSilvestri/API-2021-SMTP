package ch.heigvd.res.mailrobot.model.prank;

import ch.heigvd.res.mailrobot.config.ConfigurationManager;
import ch.heigvd.res.mailrobot.model.mail.Group;
import ch.heigvd.res.mailrobot.model.mail.Person;

import java.util.ArrayList;
import java.util.List;

public class PrankGenerator {
    private List<Group> groups = new ArrayList<>();
    private ConfigurationManager config = null;

    public PrankGenerator(){
        try{
            config = new ConfigurationManager();
        }catch(Exception e){

        }
    }
    private void generateGroups(){
        List<Person> victims = config.getVictims();
        int numGroups = config.getNumberOfGroup();
        int rest = victims.size()%numGroups;

        for(int i = 0; i < numGroups; ++i){
            for(int j = 0; j < numGroups  + (rest-- > 0 ? 1 : 0); ++j){
                //TODO add members to group and group to groups
            }
        }
    }
}
