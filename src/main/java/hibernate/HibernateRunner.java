package hibernate;

import hibernate.dao.PaymentRepository;
import hibernate.util.HibernateUtil;
import org.hibernate.*;

public class HibernateRunner {
    public static void main(String[] args) {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();

                PaymentRepository paymentRepository = new PaymentRepository(sessionFactory);
                paymentRepository.findById(1L).ifPresent(System.out::println);

                session.getTransaction().commit();
            }
        }
    }
}
