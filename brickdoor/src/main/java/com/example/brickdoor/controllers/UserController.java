package com.example.brickdoor.controllers;

import com.example.brickdoor.daos.UserDao;
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

import javax.servlet.http.HttpSession;

@Controller()
public class UserController {

  @Autowired
  private UserDao userDao;

  // This the get route, do not edit this.
  @GetMapping("/login")
  public String loginRouteGet(Model model) {
    User user = new User();
    model.addAttribute("user", user);
    return "login";
  }

  // Post route for login, handle user authentication here
  @PostMapping("/login")
  public String loginRoutePost(HttpSession session, @RequestBody User user) {
    // System.out.println(user.getUsername() + " " + user.getPassword());
    String username = user.getUsername();
    String password = user.getPassword();
    User authenticatedUser = userDao.authenticate(username, password);
    if (authenticatedUser == null) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }
    session.setAttribute("user", authenticatedUser.getId());
    return "login";
  }

  // This the get route, do not edit this.
  @GetMapping("/register")
  public String registerRouteGet(Model model) {
    User user = new User();
    model.addAttribute("user", user);
    return "register";
  }

  // Post route for login, handle user authentication here
  @PostMapping("/register")
  public String registerRoutePost(@RequestBody User user) {
     System.out.println(user.getUsername() + " " + user.getPassword());
    if (user == null || user.getUsername() == null || user.getPassword() == null || user.getEmail() == null) {
      throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Missing Register Credentials");
    }
    if (!userDao.registerUser(user)) {
      throw new ResponseStatusException(HttpStatus.CONFLICT);
    }
    return "register";
  }

  @PostMapping("/logout")
  public String logout(HttpSession session) {
    int userId = (int) session.getAttribute("user");
    session.invalidate();
    return "logout user with id: " + userId;
  }

  @PutMapping("/updateStudent")
  public String updateStudent(HttpSession session, @RequestBody Student student) {
    int userId = (int) session.getAttribute("user");
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
    int userId = (int) session.getAttribute("user");
    if (userId != company.getId() || userDao.getRole(userId) != Role.COMPANY) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }
    User updateUser = userDao.updateCompany(userId, company);
    if (updateUser == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
    return "updated company";
  }

  @DeleteMapping("/deleteUser")
  public String deleteUser(HttpSession session, User toDelete) {
    int loggedInUserId = (int) session.getAttribute("user");
    if (userDao.getRole(loggedInUserId) != Role.ADMIN) {
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }
    if (!userDao.deleteUser(toDelete.getId())) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
    return "deleted user with username: " + toDelete.getUsername();
  }

}
