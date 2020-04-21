package com.example.brickdoor.controllers;

import com.example.brickdoor.daos.UserDao;
import com.example.brickdoor.models.Admin;
import com.example.brickdoor.models.Company;
import com.example.brickdoor.models.Role;
import com.example.brickdoor.models.Student;
import com.example.brickdoor.models.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import javax.servlet.http.HttpSession;

@Controller
public class AdminController {

  @Autowired
  private UserDao userDao;

  @GetMapping("/admin/login")
  public ModelAndView adminLoginRouteGet(HttpSession session) {
    User user = session.getAttribute("user") == null ? new User() : (User) session.getAttribute("user");
    if (user.getId() != 0 && user.getRole() != Role.ADMIN) {
      return new ModelAndView("redirect:/");
    }

    ModelAndView model = new ModelAndView("admin_login");
    model.addObject("user", user);
    return model;
  }

  @PostMapping("/admin/login")
  public ModelAndView adminLoginRoutePost(HttpSession session, @ModelAttribute("user") User user) {
    String username = user.getUsername();
    String password = user.getPassword();
    User authenticatedUser = userDao.authenticate(username, password, Role.ADMIN);
    if (authenticatedUser != null) {
      session.setAttribute("user", authenticatedUser);
    }
    return new ModelAndView("redirect:/admin/");
  }

  @GetMapping("/admin/manage/users")
  public ModelAndView adminManageUsersRouteGet(HttpSession session) {
    User user = session.getAttribute("user") == null ? new User() : (User) session.getAttribute("user");
    if (user.getId() == 0 || user.getRole() != Role.ADMIN) {
      return new ModelAndView("redirect:/admin/login");
    }

    List<Student> students = userDao.getAllStudents();
    ModelAndView model = new ModelAndView("manage_users");
    model.addObject("user", user);
    model.addObject("students", students);
    return model;
  }

  @GetMapping("/admin/update/users/{sid}")
  public ModelAndView adminUpdateUsersGet(HttpSession session, @PathVariable("sid") Integer sid) {
    User user = session.getAttribute("user") == null ? new User() : (User) session.getAttribute("user");
    if (user.getId() == 0 || user.getRole() != Role.ADMIN) {
      return new ModelAndView("redirect:/admin/login");
    }
    User student2update = userDao.findStudentById(sid);
    ModelAndView model = new ModelAndView("update_student");
    model.addObject("user", user);
    model.addObject("person", student2update);
    return model;
  }

  @GetMapping("/admin/create/user")
  public ModelAndView adminCreateUserGet(HttpSession session) {
    User user = session.getAttribute("user") == null ? new User() : (User) session.getAttribute("user");
    if (user.getId() == 0 || user.getRole() != Role.ADMIN) {
      return new ModelAndView("redirect:/admin/login");
    }
    ModelAndView model = new ModelAndView("create_student");
    model.addObject("user", user);
    model.addObject("person", new Student());
    return model;
  }

  @GetMapping("/admin/update/role/{sid}")
  public ModelAndView adminSwitch(HttpSession session, @PathVariable("sid") Integer sid) {
    User user = session.getAttribute("user") == null ? new User() : (User) session.getAttribute("user");
    if (user.getId() == 0 || user.getRole() != Role.ADMIN) {
      return new ModelAndView("redirect:/admin/login");
    }

    User user2Update = userDao.findById(sid);
    ModelAndView model = new ModelAndView("switch_role");
    model.addObject("targetUser",user2Update);
    model.addObject("user", user);
    model.addObject("person", new Student());
    model.addObject("company", new Company());
    model.addObject("admin", new Admin());
    model.addObject("currentRole",user2Update.getRole().toString());
    return model;
  }




}
