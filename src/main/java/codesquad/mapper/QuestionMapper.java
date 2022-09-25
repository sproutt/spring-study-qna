package codesquad.mapper;

import codesquad.dto.question.QuestionRequestDto;
import codesquad.entity.QuestionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface QuestionMapper {
    QuestionMapper questionMapper = Mappers.getMapper(QuestionMapper.class);

    QuestionEntity dtoToEntity(QuestionRequestDto questionRequestDto);
}