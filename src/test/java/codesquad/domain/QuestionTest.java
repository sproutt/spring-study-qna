package codesquad.domain;

import org.junit.Test;

import static org.junit.Assert.*;

public class QuestionTest {

    @Test
    public void changeQuestionInfo() {
        final Question question = Question.builder()
                .title("title")
                .contents("asdf")
                .build();
        String changeTitle = "title2";
        String changeContents = "gdfjskdlfjd";
        question.changeQuestionInfo(changeTitle,changeContents);

        assertEquals(question.getTitle(),changeTitle);
        assertEquals(question.getContents(),changeContents);
    }
}