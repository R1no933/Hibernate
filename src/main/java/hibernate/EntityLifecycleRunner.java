package hibernate;

import hibernate.entity.Company;
import hibernate.entity.PersonalInfo;
import hibernate.entity.User;
import hibernate.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;



public class EntityLifecycleRunner {
    public static void main(String[] args) {
        Company company = Company.builder()
                .name("Yandex")
                .build();

        User user = User.builder()
                .username("TestUser333")
                .personalInfo(PersonalInfo.builder()
                        .firstname("First_User")
                        .lastname("FU_lastname")
                        .build())
                .company(company)
                .build();

        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
            Session firstSession = sessionFactory.openSession();
            try (firstSession) {

                firstSession.save(company);
                firstSession.save(user);

                firstSession.getTransaction().commit();
            }
        }
    }
}
