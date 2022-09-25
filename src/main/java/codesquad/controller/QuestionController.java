package codesquad.controller;

import codesquad.AppConfig;
import codesquad.dto.question.QuestionRequestDto;
import codesquad.entity.QuestionEntity;
import codesquad.mapper.QuestionMapper;
import codesquad.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class QuestionController {
    ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
    QuestionService questionService = applicationContext.getBean("questionService", QuestionService.class);

    @GetMapping("/question/post")
    public String getQuestionForm() {
        return "qna/form";
    }

    @PostMapping("/question")
    public String postQuestion(QuestionRequestDto questionRequestDto) {
        QuestionEntity questionEntity = QuestionMapper.questionMapper.dtoToEntity(questionRequestDto);
        questionService.postQuestion(questionEntity);

        return "redirect:/";
    }
}
