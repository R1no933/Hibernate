package hibernate.entity;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users", schema = "public")
@TypeDef(name = "hiber_jsonb", typeClass = JsonBinaryType.class)
public class User {

    @Id
    @GeneratedValue(generator = "users_gen_sequence", strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "users_gen_sequence", sequenceName = "user_id_seq", allocationSize = 1)
    private Long id;

    @Column(unique = true)
    private String username;

    @Embedded
    @AttributeOverride(name = "birthDate", column = @Column(name = "birth_date"))
    private PersonalInfo personalInfo;

    @Type(type = "hiber_jsonb")
    private String info;

    @Enumerated(EnumType.STRING)
    private Role role;
}
