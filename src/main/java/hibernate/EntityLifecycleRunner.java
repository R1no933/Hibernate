package hibernate;

import hibernate.entity.User;
import hibernate.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class EntityLifecycleRunner {

    private static final Logger logger = LoggerFactory.getLogger(EntityLifecycleRunner.class);
    public static void main(String[] args) {
        User user = User.builder()
                .username("TestUser")
                .firstname("Test")
                .lastname("Test")
                .build();
        logger.info("User entity is in transient state, object: {}", user);

        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
            Session firstSession = sessionFactory.openSession();
            try (firstSession) {
                Transaction transaction = firstSession.beginTransaction();
                logger.trace("Transaction is created: {}", transaction);

                firstSession.saveOrUpdate(user);
                logger.trace("User is in persistent state, User: {}, Session: {}", user, firstSession);

                firstSession.getTransaction().commit();
            }
            logger.warn("User is in persistent state, User: {}, Session is closed: {}", user, firstSession);

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
            logger.error("Exception occurred", exception);
            throw exception;
        }
    }
}
