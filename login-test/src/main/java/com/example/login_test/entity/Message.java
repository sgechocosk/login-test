package com.example.login_test.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Message {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, columnDefinition = "TEXT")
  private String content;

  @ManyToOne
  @JoinColumn(name = "team_id", nullable = false)
  private Team team;

  private LocalDateTime createdAt;

  // Getter & Setter
  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }

  public String getContent() { return content; }
  public void setContent(String content) { this.content = content; }

  public Team getTeam() { return team; }
  public void setTeam(Team team) { this.team = team; }

  public LocalDateTime getCreatedAt() { return createdAt; }
  public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
