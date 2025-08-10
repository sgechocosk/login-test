package com.example.login_test.controller;

import com.example.login_test.entity.Team;
import com.example.login_test.entity.Message;
import com.example.login_test.repository.TeamRepository;
import com.example.login_test.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/teams")
public class TeamController {

  @Autowired
  private TeamRepository teamRepository;
  @Autowired
  private MessageRepository messageRepository;

  @GetMapping("/{teamId}")
  public String viewTeam(@PathVariable Long teamId, Model model) {
    Team team = teamRepository.findById(teamId).orElse(null);
    if (team == null) {
      return "redirect:/home";
    }

    List<Message> messages = messageRepository.findByTeamIdOrderByCreatedAtAsc(teamId);
    model.addAttribute("team", team);
    model.addAttribute("messages", messages);
    return "team";
  }

  @PostMapping("/{teamId}/messages")
  public String postMessage(@PathVariable Long teamId, @RequestParam String content) {
    Message msg = new Message();
    msg.setContent(content);
    msg.setTeam(teamRepository.findById(teamId).orElse(null));
    msg.setCreatedAt(java.time.LocalDateTime.now());
    messageRepository.save(msg);

    return "redirect:/teams/" + teamId;
  }
}
