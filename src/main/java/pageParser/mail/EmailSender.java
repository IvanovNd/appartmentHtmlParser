package pageParser.mail;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Николай on 14.07.2016.
 */
public class EmailSender {
    private static Logger log = Logger.getLogger(EmailSender.class.getName());
    private Message message = null;

    public EmailSender(Session session) {
        this.message = new MimeMessage(session);
    }

    public void setText(String text) throws MessagingException {
        message.setText(text);
    }

    public void setSubject(String text) throws MessagingException {
        message.setSubject(text);
    }

    public void send(String from, String addresslist, String text) {
        try {
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(addresslist));
            setSubject("Testing Subject");
            setText(text);
            Transport.send(message);
            System.out.println("Done");
        } catch (MessagingException e) {
            log.log(Level.WARNING, "email is not sended");
            throw new RuntimeException(e);
        }
    }
}
