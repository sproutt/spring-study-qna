package com.greenfrog.qna.repository;

import com.greenfrog.qna.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {

}
