package com.example.brickdoor.controllers;

import com.example.brickdoor.daos.UserDao;
import com.example.brickdoor.models.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PutMapping;
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
  public String loginRoutePost(HttpSession session, @ModelAttribute("user") User user) {
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
  public String registerRoutePost(@ModelAttribute("user") User user) {
//     System.out.println(user.getUsername() + " " + user.getPassword());
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
  public String updateStudent(HttpSession session, @ModelAttribute("user") User user) {
    int userId = (int) session.getAttribute("user");
    User updateUser = userDao.updateStudent(userId, user);
    if (updateUser == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
    return "updated student";
  }

  @PutMapping("/updateCompany")
  public String updateCompany(HttpSession session, @ModelAttribute("user") User user) {
    int userId = (int) session.getAttribute("user");
    User updateUser = userDao.updateCompany(userId, user);
    if (updateUser == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
    return "updated company";
  }

}
