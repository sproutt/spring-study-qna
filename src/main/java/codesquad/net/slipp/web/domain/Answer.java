package codesquad.net.slipp.web.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class Answer extends BaseTimeEntity{

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_question"))
    @NonNull
    private Question question;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_user"))
    @NonNull
    private User writer;

    @Column(nullable = false)
    @Lob
    @NonNull
    private String contents;

    @Column(nullable = false)
    private Boolean deleted = false;

    public boolean match(Answer answer) {
        return this.id == answer.id;
    }

    public String getCreateYearToSecond() {
        return getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}