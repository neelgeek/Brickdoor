package com.example.brickdoor.controllers;

import com.example.brickdoor.daos.UserDao;
import com.example.brickdoor.models.Admin;
import com.example.brickdoor.models.Company;

import com.example.brickdoor.models.Role;
import com.example.brickdoor.models.Student;
import com.example.brickdoor.models.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller()
public class UserController {

  @Autowired
  private UserDao userDao;

  // This the get route, do not edit this.
  @GetMapping("/login")
  public ModelAndView loginRouteGet(HttpSession session) {
    Object userId = session.getAttribute("user");
    User user = userId == null ? new User() : userDao.findById((int) userId);
    if (user.getId() != 0) {
      return new ModelAndView("redirect:/");
    }
    ModelAndView model = new ModelAndView("login");
    model.addObject("user", user);
    return model;
  }

  // Post route for login, handle user authentication here
  @PostMapping("/login")
  public ModelAndView loginRoutePost(HttpSession session, @RequestBody User user) {
    String username = user.getUsername();
    String password = user.getPassword();
    User authenticatedUser = userDao.authenticate(username, password);
    if (authenticatedUser == null) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }
    session.setAttribute("user", authenticatedUser);
    return new ModelAndView("redirect:/");
  }

  @GetMapping("/logout")
  public ModelAndView logoutRouteGet(HttpSession session) {
    session.invalidate();
    return new ModelAndView("redirect:/");
  }

  @PostMapping("/logout")
  public String logout(HttpSession session) {
    int userId = (int) session.getAttribute("user");
    session.invalidate();
    return "logout user with id: " + userId;
  }

  // This the get route, do not edit this.
  @GetMapping("/register")
  public String registerRouteGet(Model model) {
    User user = new User();
    model.addAttribute("user", user);
    return "register";
  }

  // Post route for login, handle user authentication here
  @PostMapping("/registerStudent")
  public String registerStudentPost(@RequestBody Student student) {
    if (student == null || student.getUsername() == null || student.getPassword() == null || student.getEmail() == null) {
      throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Missing Register Credentials");
    }
    if (!userDao.registerUser(student)) {
      throw new ResponseStatusException(HttpStatus.CONFLICT);
    }
    return "registered student";
  }

  // Post route for login, handle user authentication here
  @PostMapping("/registerCompany")
  public String registerCompanyPost(@RequestBody Company company) {
    if (company == null || company.getUsername() == null || company.getPassword() == null || company.getEmail() == null) {
      throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Missing Register Credentials");
    }
    if (!userDao.registerUser(company)) {
      throw new ResponseStatusException(HttpStatus.CONFLICT);
    }
    return "registered company";
  }

  @PostMapping("/registerAdmin")
  public String registerAdminPost(@RequestBody Admin admin) {
    if (admin == null || admin.getUsername() == null || admin.getPassword() == null || admin.getEmail() == null) {
      throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Missing Register Credentials");
    }
    if (!userDao.registerUser(admin)) {
      throw new ResponseStatusException(HttpStatus.CONFLICT);
    }
    return "registered admin";
  }

  @PutMapping("/updateStudent")
  public String updateStudent(HttpSession session, @RequestBody Student student) {
    User user = (User) session.getAttribute("user");
    int userId = user.getId();

    if (userId != student.getId() || userDao.getRole(userId) != Role.STUDENT) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }
    User updateUser = userDao.updateStudent(userId, student);

    if (updateUser == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
    return "updated student";
  }

  @PutMapping("/updateCompany")
  public String updateCompany(HttpSession session, @RequestBody Company company) {
    User user = (User) session.getAttribute("user");
    int userId = user.getId();

    if (userId != company.getId() || userDao.getRole(userId) != Role.COMPANY) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }
    User updateUser = userDao.updateCompany(userId, company);

    if (updateUser == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
    return "updated company";
  }


  @PutMapping("/updateAdmin")
  public String updateAdmin(HttpSession session, @RequestBody Admin admin) {
    User user = (User) session.getAttribute("user");
    int userId = user.getId();

    if (userId != admin.getId() || userDao.getRole(userId) != Role.ADMIN) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }
    User updateUser = userDao.updateAdmin(userId, admin);

    if (updateUser == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
    return "updated admin";
  }

  @DeleteMapping("/deleteUser")
  public String deleteUser(HttpSession session, @RequestBody User toDelete) {
    User user = (User) session.getAttribute("user");
    int userId = user.getId();

    if (userDao.getRole(userId) != Role.ADMIN) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }
    if (!userDao.deleteUser(toDelete.getId())) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
    return "deleted user with username: " + toDelete.getUsername();
  }


}
