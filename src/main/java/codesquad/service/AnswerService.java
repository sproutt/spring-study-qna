package codesquad.service;

import codesquad.exception.AnswerNotFoundException;
import codesquad.model.Answer;
import codesquad.model.Question;
import codesquad.model.User;
import codesquad.repository.AnswerRepository;
import codesquad.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnswerService {

    @Autowired
    private AnswerRepository answerRepository;

    public void saveAnswer(User writer, Question question, String contents) {
        Answer answer = new Answer(writer, question,contents);
        answer.setTime(TimeUtils.getCurrentTime());
        answerRepository.save(answer);
    }

    public boolean isSameWriter(Long id, User sessionedUser) {
        return answerRepository.findById(id).orElseThrow(RuntimeException::new).isSameWriter(sessionedUser);
    }

    public void deleteAnswer(Long id) {
        Answer answer = answerRepository.findById(id).orElseThrow(AnswerNotFoundException::new);
        answer.delete();
        answerRepository.save(answer);
    }

    public Long findWriterByAnswerId(Long id) {
        return answerRepository.findById(id).orElseThrow(AnswerNotFoundException::new).findWriterId();
    }
}
