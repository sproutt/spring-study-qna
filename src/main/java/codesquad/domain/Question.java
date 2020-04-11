package codesquad.domain;

import codesquad.util.HttpSessionUtils;

import javax.persistence.*;
import javax.servlet.http.HttpSession;

@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User user;

    private String title;
    private String contents;

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getTitle() {
        return title;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public void setUserInfo(HttpSession session) {
        this.user = HttpSessionUtils.getUserFromSession(session);
    }

    public void changeQuestionInfo(Question question) {
        this.title = question.getTitle();
        this.contents = question.getContents();
    }

    public boolean isSameUserId(User sessionedUser) {
        if (this.user.getUserId().equals(sessionedUser.getUserId())) {
            return true;
        }
        return false;
    }
}
