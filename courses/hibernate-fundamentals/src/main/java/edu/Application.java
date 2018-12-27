package edu;

import edu.entity.User;
import org.hibernate.Session;

import java.time.LocalDateTime;

public class Application {
    public static void main(String[] args) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();

        User user = new User();
        user.setBirthDate(LocalDateTime.now());
        user.setCreatedDate(LocalDateTime.now());
        user.setCreatedBy("Misha");
        user.setEmailAddress("kbm3020@mail.ru");
        user.setFirstName("Paul");
        user.setLastName("Jigaur");
        user.setLastUpdatedDate(LocalDateTime.now());
        user.setLastUpdatedBy("Misha");

        session.save(user);
        session.getTransaction().commit();

        session.beginTransaction();

        User userFromDb = session.get(User.class, user.getUserId());
        user.setFirstName("John");
        session.save(userFromDb);

        session.getTransaction().commit();
        session.close();
    }
}
