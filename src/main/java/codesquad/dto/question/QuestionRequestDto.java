package codesquad.dto.question;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class QuestionRequestDto {
    private String writer;
    private String title;
    private String contents;

    @Override
    public String toString() {
        return "QuestionDto{" +
                "writer='" + writer + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                '}';
    }
}
