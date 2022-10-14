package codesquad.controller;

import codesquad.AppConfig;
import codesquad.dto.question.QuestionRequestDto;
import codesquad.dto.question.QuestionResponseDto;
import codesquad.entity.QuestionEntity;
import codesquad.mapper.QuestionMapper;
import codesquad.service.QuestionService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class QuestionController {
    ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
    QuestionService questionService = applicationContext.getBean("questionService", QuestionService.class);

    @GetMapping("/questions/post")
    public String getQuestionForm() {
        return "qna/form";
    }

    @PostMapping("/questions")
    public String postQuestion(QuestionRequestDto questionRequestDto) {
        QuestionEntity questionEntity = QuestionMapper.dtoToEntity(questionRequestDto);
        questionService.postQuestion(questionEntity);
        return "redirect:/questions";
    }

    @GetMapping("/questions")
    public String getQuestionList(Model model) {
        List<QuestionResponseDto> questions = questionService.findQuestions().stream()
                .map(questionEntity -> QuestionMapper.entityToDto(questionEntity))
                .collect(Collectors.toList());

        model.addAttribute("questions", questions);

        return "qna/list";
    }

    @GetMapping("/questions/{id}")
    public String getQuestionShow(@PathVariable String id, Model model) {
        QuestionResponseDto question = new QuestionResponseDto(questionService.findQuestion(id));

        model.addAttribute("writer", question.getWriter());
        model.addAttribute("title", question.getTitle());
        model.addAttribute("contents", question.getContents());
        model.addAttribute("date", question.getDate());

        return "qna/show";
    }

    @GetMapping("/")
    public String getHome(Model model) {
        List<QuestionEntity> questionEntityList = questionService.findQuestions();
        if (questionEntityList.size() == 0) {
            return "qna/list";
        }

        List<QuestionResponseDto> questionResponseDtoList = questionService.findQuestions().stream()
                .map(questionEntity -> QuestionMapper.entityToDto(questionEntity))
                .collect(Collectors.toList());

        model.addAttribute("questions", questionResponseDtoList);
        return "qna/list";
    }
}