package hibernate;

import hibernate.entity.Role;
import hibernate.entity.User;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;

import java.time.LocalDate;

public class HibernateRunner {
    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(User.class);
        configuration.configure();

        try (SessionFactory sessionFactory = configuration.buildSessionFactory();
            Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            User user = User.builder()
                    .username("r1n0")
                    .firstname("Dmitriy")
                    .lastname("Baskakov")
                    .birthDate(LocalDate.of(1993, 03, 19))
                    .age(30)
                    .role(Role.ADMIN)
                    .build();
            session.save(user);

            session.getTransaction().commit();
        }
    }
}
