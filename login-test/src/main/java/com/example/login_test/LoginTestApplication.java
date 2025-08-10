package com.example.login_test;

import com.example.login_test.entity.Company;
import com.example.login_test.entity.Department;
import com.example.login_test.entity.Team;
import com.example.login_test.entity.User;
import com.example.login_test.repository.CompanyRepository;
import com.example.login_test.repository.DepartmentRepository;
import com.example.login_test.repository.TeamRepository;
import com.example.login_test.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LoginTestApplication {
  public static void main(String[] args) {
    SpringApplication.run(LoginTestApplication.class, args);
  }

  @Bean
  CommandLineRunner initData(
        CompanyRepository companyRepo,
        DepartmentRepository deptRepo,
        TeamRepository teamRepo,
        UserRepository userRepo) {
    return args -> {
      // DBにデータが存在しないとき
      if (companyRepo.count() == 0) {
        Company c = new Company("SAMPLE株式会社");
        companyRepo.save(c);

        Department d1 = new Department("営業部", c);
        Department d2 = new Department("開発部", c);
        Department d3 = new Department("総務部", c);
        Department d4 = new Department("人事部", c);
        Department d5 = new Department("テスト部", c);
        deptRepo.save(d1);
        deptRepo.save(d2);
        deptRepo.save(d3);
        deptRepo.save(d4);
        deptRepo.save(d5);

        // 各部署にチームを作る（複数）
        teamRepo.save(new Team("国内営業グループ", d1));
        teamRepo.save(new Team("海外営業グループ", d1));
        teamRepo.save(new Team("営業企画", d1));

        teamRepo.save(new Team("アプリ開発", d2));
        teamRepo.save(new Team("インフラ", d2));
        teamRepo.save(new Team("QA", d2));

        teamRepo.save(new Team("総務企画", d3));
        teamRepo.save(new Team("施設管理", d3));

        teamRepo.save(new Team("採用", d4));
        teamRepo.save(new Team("研修", d4));

        teamRepo.save(new Team("テスト", d5));

        // サンプルユーザー（所属会社をセット）
        User u = new User("testuser", "pass1234", c);
        userRepo.save(u);
      }
    };
  }
}
