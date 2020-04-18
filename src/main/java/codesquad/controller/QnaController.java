package codesquad.controller;

import codesquad.domain.Qna;
import codesquad.domain.QnaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class QnaController {

    private QnaRepository qnaRepository;

    public QnaController(QnaRepository qnaRepository) {
        this.qnaRepository = qnaRepository;
    }

    @PostMapping("/questions")
    public String question(Qna qna) {
        qnaRepository.insert(qna);
        return "redirect:/";
    }

    @GetMapping("/")
    public String showQnaList() {
        return "/index";
    }
}


