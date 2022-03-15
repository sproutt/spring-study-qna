package codesquad.controller;

import codesquad.domain.Question;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Controller
public class QuestionController {
    private final List<Question> questions = new ArrayList<>();
    private final Long id = 1L;

    @PostMapping("/questions")
    public String create(@ModelAttribute Question question) {
        question.createId(id);
        question.createWrittenTime(getWrittenTime());
        questions.add(question);
        return "redirect:/";
    }

    private String getWrittenTime() {
        LocalDate nowDate = LocalDate.now();
        LocalTime nowTime = LocalTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm");
        String formatedNowTime = nowTime.format(formatter);

        return nowDate + " " + formatedNowTime;
    }

    @GetMapping("/")
    public String showAllQuestions(Model model) {
        model.addAttribute("questions", questions);
        return "index";
    }

    @GetMapping("/questions/{index}")
    public String showQuestionInfo(@PathVariable int index, Model model) {
        Question question = questions.stream()
                .filter(ques -> ques.getId() == index)
                .findAny()
                .orElseThrow(NoSuchElementException::new);
        System.out.println("글 내용 :" + question.getContents());

        model.addAttribute("question", question);
        return "qna/show";
    }
}
