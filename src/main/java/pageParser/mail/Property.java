package pageParser.mail;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;

/**
 * Created by Николай on 14.07.2016.
 */
public class Property {
    private String username = null;
    private String password = null;
    private Properties props = null;


    public Property(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Session getSession() {
        fillProperty();
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        return session;
    }

    private void fillProperty() {
        props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
    }


}
