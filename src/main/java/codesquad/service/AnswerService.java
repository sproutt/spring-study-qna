package codesquad.service;

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
        return sessionedUser.isSameUser(id);
    }

    public void deleteAnswer(Long id) {
        answerRepository.delete(answerRepository.findById(id).orElseThrow(RuntimeException::new));
    }

    public Long findWriterByAnswerId(Long id) {
        return answerRepository.findById(id).orElseThrow(RuntimeException::new).findWriterId();
    }
}
