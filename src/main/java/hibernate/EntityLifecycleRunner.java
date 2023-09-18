package hibernate;

import hibernate.entity.User;
import hibernate.util.HibernateUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class EntityLifecycleRunner {
    public static void main(String[] args) {
        User user = User.builder()
                .username("TestUser")
                .firstname("Test")
                .lastname("Test")
                .build();
        log.info("User entity is in transient state, object: {}", user);

        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
            Session firstSession = sessionFactory.openSession();
            try (firstSession) {
                Transaction transaction = firstSession.beginTransaction();
                log.trace("Transaction is created: {}", transaction);

                firstSession.saveOrUpdate(user);
                log.trace("User is in persistent state, User: {}, Session: {}", user, firstSession);

                firstSession.getTransaction().commit();
            }
            log.warn("User is in persistent state, User: {}, Session is closed: {}", user, firstSession);

            /*
            try (Session secondSession = sessionFactory.openSession()) {
                secondSession.beginTransaction();

                user.setFirstname("Refreshed");
                //secondSession.delete(user);
                //refresh/merge
                //secondSession.refresh(user);
                secondSession.merge(user);

                secondSession.getTransaction().commit();
            }
             */
        } catch (Exception exception) {
            log.error("Exception occurred", exception);
            throw exception;
        }
    }
}
