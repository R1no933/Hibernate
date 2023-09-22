package hibernate.dto;

import hibernate.entity.PersonalInfo;
import hibernate.entity.Role;

public record UserCreateDto(PersonalInfo personalInfo,
                            String username,
                            String info,
                            Role role,
                            Integer companyId) {
}
