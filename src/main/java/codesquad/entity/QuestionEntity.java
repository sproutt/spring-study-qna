package codesquad.entity;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionEntity extends BaseTimeEntity {

    private String writer;
    private String title;
    private String contents;

    @Override
    public String toString() {
        return "QuestionEntity{" +
                "writer='" + writer + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                '}';
    }
}
