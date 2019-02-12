package codesquad.utils;

import codesquad.model.question.Question;
import codesquad.model.user.User;

public class QuestionUtils {
    public boolean isWriter(Question question, User user){
        if(question.getWriter().equals(user.getName())){
            return true;
        }
        return false;
    }
}
