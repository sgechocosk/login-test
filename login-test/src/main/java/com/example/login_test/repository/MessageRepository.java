package com.example.login_test.repository;

import com.example.login_test.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
  List<Message> findByTeamIdOrderByCreatedAtAsc(Long teamId);
}
