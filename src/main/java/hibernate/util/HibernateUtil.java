package hibernate.util;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import hibernate.converter.BirthdayConverter;
import hibernate.entity.Company;
import hibernate.entity.User;
import lombok.experimental.UtilityClass;
import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.Configuration;

@UtilityClass
public class HibernateUtil {
    public static SessionFactory buildSessionFactory() {
        Configuration configuration = buildConfiguration();
        configuration.configure();

        return configuration.buildSessionFactory();
    }

    public static Configuration buildConfiguration() {
        Configuration configuration = new Configuration();
        configuration.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());
        configuration.registerTypeOverride(new JsonBinaryType());
        configuration.addAttributeConverter(new BirthdayConverter());

        return configuration;
    }
}
