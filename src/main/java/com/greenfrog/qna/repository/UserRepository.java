package com.greenfrog.qna.repository;

import com.greenfrog.qna.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
