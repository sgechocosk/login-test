package com.example.login_test.entity;

import jakarta.persistence.*;

@Entity
public class Team {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    private Department department;

    public Team() {}
    public Team(String name, Department department) {
        this.name = name;
        this.department = department;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public Department getDepartment() { return department; }
    public void setName(String name) { this.name = name; }
    public void setDepartment(Department department) { this.department = department; }
}
