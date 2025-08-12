package com.example.login_test.entity;

import jakarta.persistence.*;

@Entity
public class Department {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    private Company company;

    public Department() {}
    public Department(String name, Company company) {
        this.name = name;
        this.company = company;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public Company getCompany() { return company; }
    public void setName(String name) { this.name = name; }
    public void setCompany(Company company) { this.company = company; }
}
