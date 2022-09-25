package codesquad.mapper;

import codesquad.dto.question.QuestionRequestDto;
import codesquad.entity.QuestionEntity;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-09-25T21:39:37+0900",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 1.8.0_332 (Amazon.com Inc.)"
)
public class QuestionMapperImpl implements QuestionMapper {

    @Override
    public QuestionEntity dtoToEntity(QuestionRequestDto questionRequestDto) {
        if ( questionRequestDto == null ) {
            return null;
        }

        QuestionEntity.QuestionEntityBuilder questionEntity = QuestionEntity.builder();

        questionEntity.writer( questionRequestDto.getWriter() );
        questionEntity.title( questionRequestDto.getTitle() );
        questionEntity.contents( questionRequestDto.getContents() );

        return questionEntity.build();
    }
}
