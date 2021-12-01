package ch.heigvd.res.mailrobot.model.prank;

import ch.heigvd.res.mailrobot.config.ConfigurationManager;
import ch.heigvd.res.mailrobot.model.mail.Group;
import ch.heigvd.res.mailrobot.model.mail.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Random;

public class PrankGenerator {
    private final static Logger LOG = Logger.getLogger(PrankGenerator.class.getName());
    private final int MIN_GROUP_SIZE = 3;
    private final List<Group> groups = new ArrayList<>();
    private ConfigurationManager config = null;
    private Random random;

    public PrankGenerator(){
        try{
            random = new Random();
            config = new ConfigurationManager();
        }catch(Exception e){
            LOG.log(Level.SEVERE, e.toString());
        }
    }

    /**
     * génère la liste des pranks, attribue un message aléatoire à chaque groupe
     * @return liste des pranks
     */
    public List<Prank> generate(){
        List<String> messages = config.getMessages();
        List<Prank> pranks = new ArrayList<Prank>();
        if(generateGroups()) {
            String message;
            int temp;

            for (Group g : groups) {
                temp = random.nextInt(messages.size());
                message = messages.get(temp);
                pranks.add(new Prank(g.getMembers(),config.getWitnessesToCc(),message));
            }
        }
        return pranks;
    }

    /**
     * génère les n groupes contenant les victimes
     */
    private boolean generateGroups(){
        List<Person> victims = config.getVictims();
        int numGroups = config.getNumberOfGroup();
        int rest = victims.size()% numGroups;

        int groupSize = victims.size()/ numGroups;
        if(groupSize >= MIN_GROUP_SIZE) {
            for (int i = 0; i < numGroups; ++i) {
                Group temp = new Group();
                for (int j = 0; j < +(rest-- > 0 ? 1 : 0); ++j) {
                    temp.addMember(victims.get(j + i));
                }
                groups.add(temp);
            }
            return true;
        }else{
            LOG.log(Level.SEVERE, "Group has less than 3 people");
            return false;
        }
    }
}
