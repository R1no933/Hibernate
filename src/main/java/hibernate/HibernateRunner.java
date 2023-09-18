package hibernate;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import hibernate.converter.BirthdayConverter;
import hibernate.entity.Birthday;
import hibernate.entity.Role;
import hibernate.entity.User;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;

import javax.persistence.AttributeConverter;
import java.time.LocalDate;

public class HibernateRunner {
    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(User.class);
        configuration.registerTypeOverride(new JsonBinaryType());
        configuration.addAttributeConverter((AttributeConverter) new BirthdayConverter());
        configuration.configure();

        try (SessionFactory sessionFactory = configuration.buildSessionFactory();
            Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            User user = User.builder()
                    .username("r1n0123")
                    .firstname("Dmitriy")
                    .lastname("Baskakov")
                    .birthDate(new Birthday(LocalDate.of(1993, 03, 19)))
                    .info("""
                            {
                            "name" : "Dmitriy",
                            "ID" : 334089
                            }
                            """)
                    .role(Role.ADMIN)
                    .build();
            session.update(user);

            session.getTransaction().commit();
        }
    }
}
