package hibernate;

import hibernate.dao.PaymentRepository;
import hibernate.util.HibernateUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.*;

import javax.transaction.Transactional;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@Slf4j
public class HibernateRunner {
    @Transactional
    public static void main(String[] args) {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {

            Session session = (Session) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(), new Class[]{Session.class},
                    (proxy, method, args1) -> method.invoke(sessionFactory.getCurrentSession(), args1));
            session.beginTransaction();

            PaymentRepository paymentRepository = new PaymentRepository(session);
            paymentRepository.findById(1L).ifPresent(System.out::println);

            session.getTransaction().commit();
        }
    }
}

