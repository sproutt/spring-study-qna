package codesquad.domain;

import codesquad.util.HttpSessionUtils;

import javax.persistence.*;
import javax.servlet.http.HttpSession;

@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String writer;

    private String title;
    private String contents;

    public Long getId() {
        return id;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(HttpSession session) {
        User user = HttpSessionUtils.getUserFromSession(session);
        this.writer = user.getName();
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

    public void changeQuestionInfo(Question question) {
        this.title = question.getTitle();
        this.contents = question.getContents();
    }

}
