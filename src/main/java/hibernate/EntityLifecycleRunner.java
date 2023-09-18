package hibernate;

import hibernate.entity.PersonalInfo;
import hibernate.entity.User;
import hibernate.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;



public class EntityLifecycleRunner {
    public static void main(String[] args) {
        User user = User.builder()
                .username("TestUser3")
                .personalInfo(PersonalInfo.builder()
                        .firstname("First_User")
                        .lastname("FU_lastname")
                        .build())
                .build();

        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
            Session firstSession = sessionFactory.openSession();
            try (firstSession) {
                Transaction transaction = firstSession.beginTransaction();
                firstSession.saveOrUpdate(user);
                firstSession.getTransaction().commit();
            }
        }
    }
}
