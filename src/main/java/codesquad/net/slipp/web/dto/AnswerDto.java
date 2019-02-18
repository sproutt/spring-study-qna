package codesquad.net.slipp.web.dto;


import codesquad.net.slipp.web.domain.Question;
import codesquad.net.slipp.web.domain.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class AnswerDto {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_question"))
    private Question question;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_user"))
    private User writer;

    @Column(nullable = false)
    @Lob
    private String content;

    @Column(nullable = false)
    private Boolean deleted = false;

    public AnswerDto(Question question, User writer, String content) {
        this.question = question;
        this.writer = writer;
        this.content = content;
    }



}
