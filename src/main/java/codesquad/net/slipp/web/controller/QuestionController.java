package codesquad.net.slipp.web.controller;

import codesquad.net.slipp.web.model.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Controller
public class QuestionController {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    private List<Question> questions = new ArrayList<Question>();

    @RequestMapping(value = "/qna/form", method = RequestMethod.GET)
    String getQuestionForm() {
        //질문 등록폼 불러오기

        return "/qna/form";
    }

    @RequestMapping(value = "/questions", method = RequestMethod.POST)
    String PostQuestion(Question question) {
        //질문 등록하기

        questions.add(question);
        return "redirect:/";
    }

    @RequestMapping(value="/questions/{index}")
    String getQuestionFormDetail(@PathVariable int index, Model model){

        model.addAttribute("question", questions.get(index));
        return "/qna/showDetail";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    String getQuestionList(Model model) {
        // 질문 리스트 불러오기
        model.addAttribute("questions", questions);
        return "/qna/show";
    }

    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    String get() {

        return "redirect:/";
    }

}
