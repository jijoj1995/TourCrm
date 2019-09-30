package service;
import com.sun.mail.util.MailSSLSocketFactory;
import main.InventoryConfig;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailService {




    public  void generateAndSendEmail(String emailRecipient) throws Exception{

         Properties mailServerProperties;
         Session getMailSession;
         MimeMessage generateMailMessage;
        InventoryConfig inventoryConfig=InventoryConfig.getInstance();
            String adminEmailId=inventoryConfig.getAppProperties().getProperty("adminEmailId");
            String adminEmailPassword=inventoryConfig.getAppProperties().getProperty("adminEmailPassword");
            String emailSubject=inventoryConfig.getAppProperties().getProperty("emailSubject");
            String emailMessage=inventoryConfig.getAppProperties().getProperty("emailMessage");
            MailSSLSocketFactory sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);


        // Step1
            System.out.println("\n 1st ===> setup Mail Server Properties..");
            mailServerProperties = System.getProperties();
            mailServerProperties.put("mail.smtp.port", "587");
            mailServerProperties.put("mail.smtp.auth", "true");
            mailServerProperties.put("mail.smtp.starttls.enable", "true");
            System.out.println("Mail Server Properties have been setup successfully..");

            mailServerProperties.put("mail.imap.ssl.trust", "*");
            mailServerProperties.put("mail.imap.ssl.socketFactory", sf);
            // Step2
        System.out.println("\n\n 2nd ===> get Mail Session..");
        getMailSession = Session.getDefaultInstance(mailServerProperties, null);
        generateMailMessage = new MimeMessage(getMailSession);
        generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(emailRecipient));
        //generateMailMessage.addRecipient(Message.RecipientType.CC, new InternetAddress(""));
        generateMailMessage.setSubject(emailSubject);
        //String emailBody = "Test email by Crunchify.com JavaMail API example. " + "<br><br> Regards, <br>Crunchify Admin";
        generateMailMessage.setContent(emailMessage, "text/html");
        System.out.println("Mail Session has been created successfully..");

        // Step3
        System.out.println("\n\n 3rd ===> Get Session and Send mail");
        Transport transport = getMailSession.getTransport("smtp");

        // Enter your correct gmail UserID and Password
        // if you have 2FA enabled then provide App Specific Password
        transport.connect("smtp.gmail.com", adminEmailId, adminEmailPassword);
        transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
        transport.close();

    }


}