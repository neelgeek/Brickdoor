package com.example.brickdoor.controllers;

import com.example.brickdoor.models.User;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class AppController {

  @GetMapping("/")
  public ModelAndView indexRoute(HttpSession session) {
    User user = session.getAttribute("user") == null ? new User() : (User) session.getAttribute("user");
    ModelAndView modelAndView = new ModelAndView("index");
    modelAndView.addObject("user",user);
    return modelAndView;
  }


  @GetMapping("/company")
  public String companyRoute() {
    return "company";
  }

}
