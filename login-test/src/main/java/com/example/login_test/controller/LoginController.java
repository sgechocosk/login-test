package com.example.login_test.controller;

import com.example.login_test.entity.User;
import com.example.login_test.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {
    
  @Autowired
  private UserRepository userRepository;

  // login.htmlを探す
  @GetMapping("/login")
  public String loginPage() {
    return "login";
  }

  // ログインボタンが押されたときの処理
  @PostMapping("/login")
  public String login(@RequestParam String username,
                      @RequestParam String password,
                      HttpSession session,
                      Model model) {
    User user = userRepository.findByUsernameAndPassword(username, password);
    if (user != null) {
      // セッションにユーザー情報を保持
      session.setAttribute("username", user.getUsername());
      if (user.getCompany() != null) {
        session.setAttribute("companyId", user.getCompany().getId());
        session.setAttribute("companyName", user.getCompany().getName());
      }
      return "redirect:/home";
    }
    model.addAttribute("error", "IDまたはパスワードが違います");
    return "login";
  }

  @PostMapping("/logout")
  public String logout(HttpSession session) {
    session.invalidate();
    return "redirect:/login";
  }

    // @PostMapping("/delete")
    // public String deleteAccount(@RequestParam String username) {
    //     User user = userRepository.findByUsername(username);
    //     if (user != null) {
    //         userRepository.delete(user);
    //     }
    //     return "redirect:/login";
    // }

    // @GetMapping("/register")
    // public String registerPage() {
    //     return "register";
    // }

    // @PostMapping("/register")
    // public String register(@RequestParam String username,
    //                        @RequestParam String password,
    //                        Model model) {
    //     if (userRepository.findByUsername(username) != null) {
    //         model.addAttribute("error", "このユーザー名は既に使われています");
    //         return "register";
    //     }
    //     userRepository.save(new User(username, password));
    //     model.addAttribute("success", "登録完了！ログインしてください");
    //     return "login";
    // }
}
