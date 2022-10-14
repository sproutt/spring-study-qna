package codesquad.mapper;

import codesquad.dto.question.QuestionRequestDto;
import codesquad.dto.question.QuestionResponseDto;
import codesquad.entity.QuestionEntity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class QuestionMapper {

    public static QuestionEntity dtoToEntity(QuestionRequestDto questionRequestDto) {
        if (questionRequestDto == null){
            return null;
        }

        QuestionEntity.QuestionEntityBuilder questionEntity = QuestionEntity.builder();

        questionEntity.writer(questionRequestDto.getWriter());
        questionEntity.title(questionRequestDto.getTitle());
        questionEntity.contents(questionRequestDto.getContents());

        return questionEntity.build();
    }

    public static QuestionResponseDto entityToDto(QuestionEntity questionEntity) {
        if (questionEntity == null) {
            return null;
        }

        QuestionResponseDto.QuestionResponseDtoBuilder questionResponseDto = QuestionResponseDto.builder();

        questionResponseDto.writer(questionEntity.getWriter());
        questionResponseDto.title(questionEntity.getTitle());
        questionResponseDto.contents(questionEntity.getContents());
        questionResponseDto.date(localTimeToString(questionEntity.getCreatedDate()));

        return questionResponseDto.build();
    }

    public static String localTimeToString(LocalDateTime localDateTime) {
        String stringLocalTime = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        return stringLocalTime;
    }
}
