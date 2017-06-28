package pageParser;

import pageParser.hibernate.AppartmentPO;
import pageParser.hibernate.HibernateUtil;
import pageParser.mail.EmailSender;
import pageParser.mail.Property;
import org.hibernate.Session;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class App {
    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(new FileReader("new.txt"))) {
            String line = br.readLine();

            boolean appartment = false;
            Set<AppartmentPO> appartmentList = new HashSet<>();
            AppartmentPO newAppartment = new AppartmentPO();
            while (line != null) {
                //определяет начало и конец списка квартир
                appartment = isAppartment(line, appartment);
                if (appartment) {
                    if (line.contains("Комиссия")) {
                        br.readLine();
                    } else if (line.contains("к.кв.")) {
                        newAppartment = new AppartmentPO();
                        StringTokenizer stringTokenizer = new StringTokenizer(line, "\t");
                        newAppartment.setId(stringTokenizer.nextToken());
                        newAppartment.setRooms(stringTokenizer.nextToken());
                    } else if (line.contains("д.")) {
                        newAppartment.setAdress(line);
                        appartmentList.add(newAppartment);
                    }
                }
                line = br.readLine();
            }
            System.out.print(appartmentList.size());
            System.out.print(appartmentList);

            saveIntoDB(appartmentList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void saveIntoDB(Set<AppartmentPO> appartmentList) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        List<AppartmentPO> newAppartmentPOS = new ArrayList<>();
        for (AppartmentPO appartmentPO : appartmentList) {
            AppartmentPO existedAppartment = session.get(AppartmentPO.class, appartmentPO.getId());
            if (existedAppartment == null) {
                session.save(appartmentPO);
                newAppartmentPOS.add(appartmentPO);
            }
        }
        session.getTransaction().commit();
        session.close();
        if (newAppartmentPOS.size() > 1) {
            System.out.print("MAIL SEND");
            String username = "";
            String password = "";
            EmailSender emailSender = new EmailSender(new Property(username, password).getSession());
            emailSender.send(username, username, newAppartmentPOS.size()+ "\n" + newAppartmentPOS.toString());
        }

        HibernateUtil.shutdown();
    }

    private static boolean isAppartment(String line, boolean appartment) {
        if (line.equals("Ставка\tУсловия сделки\tПримечания\tФото")) {
            appartment = true;
        } else if (line.contains("Страницы")) {
            appartment = false;
        }
        return appartment;
    }
}
