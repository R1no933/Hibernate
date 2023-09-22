package hibernate;

import hibernate.dao.CompanyRepository;
import hibernate.dao.PaymentRepository;
import hibernate.dao.UserRepository;
import hibernate.dto.UserCreateDto;
import hibernate.entity.PersonalInfo;
import hibernate.entity.Role;
import hibernate.entity.User;
import hibernate.interceptor.TransactionalInterceptor;
import hibernate.mapper.CompanyReadMapper;
import hibernate.mapper.UserCreateMapper;
import hibernate.mapper.UserReadMapper;
import hibernate.service.UserService;
import hibernate.util.HibernateUtil;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import org.hibernate.*;

import javax.transaction.Transactional;
import java.lang.InstantiationException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.time.LocalDate;

@Slf4j
public class HibernateRunner {
    @Transactional
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {

            Session session = (Session) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(), new Class[]{Session.class},
                    (proxy, method, args1) -> method.invoke(sessionFactory.getCurrentSession(), args1));
            //session.beginTransaction();

            CompanyReadMapper companyReadMapper = new CompanyReadMapper();
            CompanyRepository companyRepository = new CompanyRepository(session);
            UserReadMapper userReadMapper = new UserReadMapper(companyReadMapper);
            UserRepository userRepository = new UserRepository(session);
            UserCreateMapper userCreateMapper = new UserCreateMapper(companyRepository);
            PaymentRepository paymentRepository = new PaymentRepository(session);
            //UserService userService = new UserService(userRepository, userReadMapper, userCreateMapper);
            TransactionalInterceptor interceptor = new TransactionalInterceptor(sessionFactory);

            UserService byteBuddyUserService = new ByteBuddy()
                    .subclass(UserService.class)
                    .method(ElementMatchers.any())
                    .intercept(MethodDelegation.to(interceptor))
                    .make().load(UserService.class.getClassLoader())
                    .getLoaded()
                    .getDeclaredConstructor(UserRepository.class, UserReadMapper.class, UserCreateMapper.class)
                    .newInstance(userRepository, userReadMapper, userCreateMapper);

            //userService.findById(1L).ifPresent(System.out::println);
            byteBuddyUserService.findById(1L).ifPresent(System.out::println);

            UserCreateDto UserCreateDto = new UserCreateDto(
                    PersonalInfo.builder()
                            .firstname("Dmitriy")
                            .lastname("Baskakov")
                            .birthDate(LocalDate.now())
                            .build(),
                    "db@yandex.ru",
                    null,
                    Role.ADMIN,
                    1
            );
            byteBuddyUserService.create(UserCreateDto);
            //userService.create(UserCreateDto);

            //session.getTransaction().commit();
        }
    }
}

