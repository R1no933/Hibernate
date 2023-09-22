package hibernate.dto;

import hibernate.entity.PersonalInfo;
import hibernate.entity.Role;

public record UserReadDto(Long id,
                          PersonalInfo personalInfo,
                          String username,
                          String info,
                          Role role,
                          CompanyReadDto companyReadDto) {
}
