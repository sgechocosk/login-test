package com.example.login_test.repository;

import com.example.login_test.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    List<Department> findByCompanyId(Long companyId);
}
