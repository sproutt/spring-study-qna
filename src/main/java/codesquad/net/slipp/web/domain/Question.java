package codesquad.net.slipp.web.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
public class Question {

    @Id
    @GeneratedValue
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;

    @JsonIgnore
    @OneToMany(mappedBy = "question")
    private List<Answer> answers;

    private LocalDateTime createTime = LocalDateTime.now();

    @Column(nullable = false, length = 200)
    private String title;

    @Lob
    private String contents;

    @Column(nullable = false)
    private Boolean deleted = false;

    public void update(Question modifiedQuestion) {
        this.title = modifiedQuestion.title;
        this.contents = modifiedQuestion.contents;
    }

    public int getSize() {

        return answers.stream()
                .filter(answer -> !answer.getDeleted())
                .collect(Collectors.toList())
                .size();
    }

}
