package hibernate;

import hibernate.converter.BirthdayConverter;
import hibernate.entity.Birthday;
import hibernate.entity.Role;
import hibernate.entity.User;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;

import java.time.LocalDate;

public class HibernateRunner {
    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(User.class);
        configuration.addAttributeConverter(new BirthdayConverter());
        configuration.configure();

        try (SessionFactory sessionFactory = configuration.buildSessionFactory();
            Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            User user = User.builder()
                    .username("r1n0333")
                    .firstname("Dmitriy")
                    .lastname("Baskakov")
                    .birthDate(new Birthday(LocalDate.of(1993, 03, 19)))
                    .role(Role.ADMIN)
                    .build();
            session.save(user);

            session.getTransaction().commit();
        }
    }
}
