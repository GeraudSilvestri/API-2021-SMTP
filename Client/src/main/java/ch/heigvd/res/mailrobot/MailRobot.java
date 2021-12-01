package ch.heigvd.res.mailrobot;

import ch.heigvd.res.mailrobot.model.prank.PrankGenerator;
import ch.heigvd.res.mailrobot.smtp.SmtpClient;

public class MailRobot {
    public static void main(String[] args){
        try {
            PrankGenerator gen = new PrankGenerator();
            SmtpClient client = new SmtpClient(gen.generate());
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
