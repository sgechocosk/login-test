package com.example.login_test.repository;

import com.example.login_test.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team, Long> {
    List<Team> findByDepartmentId(Long departmentId);
}
