package codesquad.model.user;

import codesquad.model.answer.Answer;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    @Column(nullable = false, length = 20)
    private String userId;

    @Getter
    @Setter
    @Column(nullable = false)
    private String password;

    @Getter
    @Setter
    @Column(nullable = false, length = 10)
    private String name;

    @Getter
    @Setter
    @Column(nullable = false)
    private String email;

    @Getter
    @Setter
    @OneToMany(mappedBy = "question")
    private List<Answer> answers;

    public void update(User newUser) {
        this.name = newUser.getName();
        this.email = newUser.getEmail();
    }
}
