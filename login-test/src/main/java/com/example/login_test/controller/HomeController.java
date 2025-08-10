package com.example.login_test.controller;

import com.example.login_test.entity.Company;
import com.example.login_test.entity.Department;
import com.example.login_test.entity.Team;
import com.example.login_test.entity.User;
import com.example.login_test.entity.Message;
import com.example.login_test.repository.CompanyRepository;
import com.example.login_test.repository.DepartmentRepository;
import com.example.login_test.repository.TeamRepository;
import com.example.login_test.repository.UserRepository;
import com.example.login_test.repository.MessageRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class HomeController {

  @Autowired
  private CompanyRepository companyRepository;
  @Autowired
  private DepartmentRepository departmentRepository;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private TeamRepository teamRepository;
  @Autowired
  private MessageRepository messageRepository;

  // ホーム画面
  @GetMapping({"/", "/home"})
  public String home(HttpSession session, Model model) {
    Object username = session.getAttribute("username");
    Object companyIdObj = session.getAttribute("companyId");

    if (username == null || companyIdObj == null) {
      return "redirect:/login";
    }

    Long companyId = (Long) companyIdObj;
    Company company = companyRepository.findById(companyId).orElse(null);
    List<Department> departments = departmentRepository.findByCompanyId(companyId);

    User user = userRepository.findByUsername((String) username);
    model.addAttribute("user", user);
    model.addAttribute("username", username);
    model.addAttribute("company", company);
    model.addAttribute("departments", departments);
    return "home";
  }

  // プロフィール画面
  @GetMapping("/profile")
  public String profile(HttpSession session, Model model) {
    Object username = session.getAttribute("username");
    if (username == null) {
      return "redirect:/login";
    }
    User user = userRepository.findByUsername((String) username);
    model.addAttribute("user", user);
    return "profile";
  }

  // 設定画面
  @GetMapping("/settings")
  public String settings(HttpSession session, Model model) {
    Object username = session.getAttribute("username");
    if (username == null) {
      return "redirect:/login";
    }
    User user = userRepository.findByUsername((String) username);
    model.addAttribute("user", user);
    return "settings";
  }

  // チームページ
  @GetMapping("/teams/{id}")
  public String teamPage(@PathVariable Long id, HttpSession session, Model model) {
    Object username = session.getAttribute("username");
    if (username == null) {
      return "redirect:/login";
    }

    Team team = teamRepository.findById(id).orElse(null);
    if (team == null) {
      return "redirect:/home";
    }

    List<Message> messages = messageRepository.findByTeamIdOrderByCreatedAtAsc(id);

    model.addAttribute("team", team);
    model.addAttribute("messages", messages);
    return "team";
  }

  // API: 部署IDからチーム一覧を返す
  @GetMapping("/api/departments/{deptId}/teams")
  @ResponseBody
  public ResponseEntity<List<Team>> getTeams(@PathVariable Long deptId) {
    List<Team> teams = teamRepository.findByDepartmentId(deptId);
    return ResponseEntity.ok(teams);
  }
}
