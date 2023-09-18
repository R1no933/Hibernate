package hibernate;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import hibernate.converter.BirthdayConverter;
import hibernate.entity.Birthday;
import hibernate.entity.Role;
import hibernate.entity.User;
import hibernate.util.HibernateUtil;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.AttributeConverter;
import java.time.LocalDate;

public class HibernateRunner {
    public static void main(String[] args) {
        /*
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            User user = User.builder()
                    .username("r1n01234444")
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
         */
    }
}
