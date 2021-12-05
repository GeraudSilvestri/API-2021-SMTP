package ch.heigvd.res.mailrobot;

import ch.heigvd.res.mailrobot.config.ConfigurationManager;
import ch.heigvd.res.mailrobot.model.prank.PrankGenerator;
import ch.heigvd.res.mailrobot.smtp.SmtpClient;

public class MailRobot {
    public static void main(String[] args){
        try {
            ConfigurationManager config = new ConfigurationManager();
            PrankGenerator gen = new PrankGenerator(config);
            SmtpClient client = new SmtpClient(gen.generate(), config);
            if(client.start() == 0){
                System.out.println("Mails envoyés");
            }
            else{
                System.out.println("Erreur");
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
