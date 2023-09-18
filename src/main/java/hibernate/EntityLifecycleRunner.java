package hibernate;

import hibernate.entity.User;
import hibernate.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.sql.SQLException;

public class EntityLifecycleRunner {
    public static void main(String[] args) {
        User user = User.builder()
                .username("TestUser")
                .firstname("Test")
                .lastname("Test")
                .build();

        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
            try (Session firstSession = sessionFactory.openSession()) {
                firstSession.beginTransaction();

                firstSession.saveOrUpdate(user);

                firstSession.getTransaction().commit();
            }

            try (Session secondSession = sessionFactory.openSession()) {
                secondSession.beginTransaction();

                user.setFirstname("Refreshed");
                //secondSession.delete(user);
                //refresh/merge
                //secondSession.refresh(user);
                secondSession.merge(user);

                secondSession.getTransaction().commit();
            }
        }
    }
}
