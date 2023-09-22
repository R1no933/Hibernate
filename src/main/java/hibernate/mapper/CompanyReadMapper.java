package hibernate.mapper;

import hibernate.dto.CompanyReadDto;
import hibernate.entity.Company;

public class CompanyReadMapper implements Mapper<Company, CompanyReadDto> {
    @Override
    public CompanyReadDto mapFrom(Company object) {
        return new CompanyReadDto(
                object.getId(),
                object.getName(),
                object.getLocales()
        );
    }
}
