package codesquad.dto.question;

import codesquad.entity.QuestionEntity;
import codesquad.mapper.QuestionMapper;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class QuestionResponseDto {
    private String writer;
    private String title;
    private String contents;
    private String date;

    public QuestionResponseDto(QuestionEntity questionEntity) {
        writer = questionEntity.getWriter();
        title = questionEntity.getTitle();
        contents = questionEntity.getContents();
        date = QuestionMapper.localTimeToString(questionEntity.getCreatedDate());
    }
}