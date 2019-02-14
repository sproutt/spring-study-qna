package codesquad.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@Entity
public class Answer {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_question"))
    private Question question;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_writer"))
    private User writer;

    @Lob
    private String contents;

    private LocalDateTime creatDate = LocalDateTime.now();

    private boolean deleted = false;

    public String getFormattedCreatDate() {
        if (creatDate == null) {
            return "";
        }
        return creatDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

}
