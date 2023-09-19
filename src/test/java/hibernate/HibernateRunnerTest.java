package hibernate;

import hibernate.entity.*;
import hibernate.util.HibernateUtil;
import lombok.Cleanup;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Arrays;

import static java.util.Optional.*;
import static java.util.stream.Collectors.joining;

class HibernateRunnerTest {

    @Test
    void checkManyToMany() {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            User user = session.get(User.class, 8L);
            Chat chat = session.get(Chat.class, 1L);

            UserChat userChat = UserChat.builder()
                    .createdAt(Instant.now())
                    .createdBy(user.getUsername())
                    .build();

            userChat.setUser(user);
            userChat.setChat(chat);

            session.save(userChat);

            session.getTransaction().commit();
        }
    }

    /*
    @Test
    void checkOneToOne() {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
            Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            User user = User.builder()
                    .username("TestUser4")
                    .build();

            Profile profile = Profile.builder()
                    .language("ru")
                    .street("test_Street")
                    .build();

            profile.setUser(user);
            session.save(user);


            session.getTransaction().commit();
        }
    }

    @Test
    void deleteCompany() {
        @Cleanup SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup Session session = sessionFactory.openSession();

        session.beginTransaction();

        Company company = session.get(Company.class, 2);
        session.delete(company);

        session.getTransaction().commit();
    }

    @Test
    void addUserToNewCompany() {
        @Cleanup SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup Session session = sessionFactory.openSession();

        session.beginTransaction();

        Company company = Company.builder()
                .name("Amazon")
                .build();
        User user = User.builder()
                .username("NewUser")
                .build();
        company.addUser(user);

        session.save(company);

        session.getTransaction().commit();
    }

    @Test
    void oneToMany() {
        @Cleanup SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup Session session = sessionFactory.openSession();

        session.beginTransaction();

        Company company = session.get(Company.class, 2);
        System.out.println("");

        session.getTransaction().commit();

    }

    /*
    @Test
    void checkReflectionApi() throws SQLException, IllegalAccessException {
        User user = User.builder()
                .build();

        String sql = """
                insert
                into
                %s
                (%s)
                values
                (%s)
                """;

        String tableName = ofNullable(user.getClass().getAnnotation(Table.class))
                .map(tabbleAnnotation -> tabbleAnnotation.schema() + "." + tabbleAnnotation.name())
                .orElse(user.getClass().getName());

        Field[] declaredFields = user.getClass().getDeclaredFields();

        String columnName = Arrays.stream(declaredFields)
                .map(field -> ofNullable(field.getAnnotation(Column.class))
                        .map(Column::name)
                        .orElse(field.getName()))
                .collect(joining(", "));

        String columnValues = Arrays.stream(declaredFields)
                .map(field -> "?")
                .collect(joining(", "));

        System.out.println(sql.formatted(tableName, columnName, columnValues));
        Connection connection = null;
        PreparedStatement preparedStatement = connection.prepareStatement(sql.formatted(tableName, columnName, columnValues));
        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);
            preparedStatement.setObject(1, declaredField.get(user));
        }
    }
     */
}