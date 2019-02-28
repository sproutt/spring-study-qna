package codesquad.model.user;

import codesquad.model.answer.Answer;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String userId;


    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 10)
    private String name;

    @Column(nullable = false)
    private String email;

    @OneToMany(mappedBy = "question")
    private List<Answer> answers;

    public void update(User newUser) {
        this.name = newUser.getName();
        this.email = newUser.getEmail();
    }

    public boolean isCorrectPassword(String password){
        if(this.password.equals(password)){
            return true;
        }
        return false;
    }
}
