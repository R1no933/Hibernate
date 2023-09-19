package hibernate.entity;

import lombok.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.hibernate.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "users")
@EqualsAndHashCode(of = "users")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Builder.Default
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL) //Can use without many to one
    @OrderBy("username DESC, personalInfo.lastname ASC")
    private Set<User> users = new HashSet<>();

    @Builder.Default
    @ElementCollection
    @CollectionTable(name = "company_locale")
    private List<LocaleInfo> locales = new ArrayList<>();

    public void addUser(User user) {
        users.add(user);
        user.setCompany(this);
    }
}
