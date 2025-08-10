package com.example.login_test.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;

    @ManyToOne
    private Company company;

    public User() {}
    public User(String username, String password, Company company) {
        this.username = username;
        this.password = password;
        this.company = company;
    }

    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public Company getCompany() { return company; }

    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setCompany(Company company) { this.company = company; }
}
