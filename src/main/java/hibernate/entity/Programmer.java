package hibernate.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@PrimaryKeyJoinColumn(name = "id")
public class Programmer extends User {
    @Enumerated(EnumType.STRING)
    private Language language;

    @Builder
    public Programmer(Long id, PersonalInfo personalInfo, String username, String info, Role role, Company company, Profile profile, List<UserChat> userChats, Language language) {
        super(id, personalInfo, username, info, role, company, profile, userChats);
        this.language = language;
    }
}
