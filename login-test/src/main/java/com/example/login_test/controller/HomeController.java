package com.example.login_test.controller;

import com.example.login_test.entity.Company;
import com.example.login_test.entity.Department;
import com.example.login_test.entity.Team;
import com.example.login_test.entity.User;
import com.example.login_test.repository.CompanyRepository;
import com.example.login_test.repository.DepartmentRepository;
import com.example.login_test.repository.TeamRepository;
import com.example.login_test.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

  // ホーム画面
  @GetMapping({"/", "/home"})
  public String home(HttpSession session, Model model, @RequestParam(required = false) Long dept) {
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

    // 部署IDが指定されている場合、その部署を選択状態にする
    if (dept != null) {
      Department selectedDept = departmentRepository.findById(dept).orElse(null);
      if (selectedDept != null && selectedDept.getCompany().getId().equals(companyId)) {
        List<Team> teams = teamRepository.findByDepartmentId(dept);
        model.addAttribute("selectedDepartment", selectedDept);
        model.addAttribute("teams", teams);
      }
    }
    
    return "home";
  }

  // プロフィール画面
  @GetMapping("/profile")
  public String profile(HttpSession session, Model model) {
    Object username = session.getAttribute("username");
    Object companyIdObj = session.getAttribute("companyId");
    
    if (username == null || companyIdObj == null) {
      return "redirect:/login";
    }
    
    User user = userRepository.findByUsername((String) username);
    Long companyId = (Long) companyIdObj;
    Company company = companyRepository.findById(companyId).orElse(null);
    
    model.addAttribute("user", user);
    model.addAttribute("company", company);
    return "profile";
  }

  // 設定画面
  @GetMapping("/settings")
  public String settings(HttpSession session, Model model) {
    Object username = session.getAttribute("username");
    Object companyIdObj = session.getAttribute("companyId");
    
    if (username == null || companyIdObj == null) {
      return "redirect:/login";
    }
    
    User user = userRepository.findByUsername((String) username);
    Long companyId = (Long) companyIdObj;
    Company company = companyRepository.findById(companyId).orElse(null);
    
    model.addAttribute("user", user);
    model.addAttribute("company", company);
    return "settings";
  }

  // API: 部署IDからチーム一覧を返す
  @GetMapping("/api/departments/{deptId}/teams")
  @ResponseBody
  public ResponseEntity<List<Team>> getTeams(@PathVariable Long deptId) {
    List<Team> teams = teamRepository.findByDepartmentId(deptId);
    return ResponseEntity.ok(teams);
  }

  // API: 部署IDから部署情報を返す（部署名取得用）
  @GetMapping("/api/departments/{deptId}")
  @ResponseBody
  public ResponseEntity<Department> getDepartment(@PathVariable Long deptId) {
    Department dept = departmentRepository.findById(deptId).orElse(null);
    if (dept == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(dept);
  }

  // API: 新しいチームを作成
  @PostMapping("/api/teams")
  @ResponseBody
  public ResponseEntity<String> createTeam(@RequestBody Map<String, Object> request) {
    try {
      String name = (String) request.get("name");
      Long departmentId = Long.valueOf(request.get("departmentId").toString());
      
      Department department = departmentRepository.findById(departmentId).orElse(null);
      if (department == null) {
        return ResponseEntity.badRequest().body("部署が見つかりません");
      }
      
      Team team = new Team(name, department);
      teamRepository.save(team);
      
      return ResponseEntity.ok("チームが作成されました");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("チーム作成に失敗しました");
    }
  }
}
