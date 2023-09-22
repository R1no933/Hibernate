package hibernate.dao;

import hibernate.entity.Company;
import org.hibernate.SessionFactory;

import javax.persistence.EntityManager;

public class CompanyRepository extends RepositoryBase<Integer, Company> {

    public CompanyRepository(EntityManager entityManager) {
        super(Company.class, entityManager);
    }
}
