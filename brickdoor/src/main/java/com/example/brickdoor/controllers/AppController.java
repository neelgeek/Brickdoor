package com.example.brickdoor.controllers;

import com.example.brickdoor.daos.ReviewDao;
import com.example.brickdoor.models.Review;
import com.example.brickdoor.models.Role;
import com.example.brickdoor.models.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import javax.servlet.http.HttpSession;

@Controller
public class AppController {

  @Autowired
  private ReviewDao reviewDao;

  @GetMapping("/")
  public ModelAndView indexRoute(HttpSession session) {
    User user = session.getAttribute("user") == null ? new User() : (User) session.getAttribute("user");
    List<Review> reviewList = reviewDao.findAllReview();
    ModelAndView modelAndView = new ModelAndView("index");
    modelAndView.addObject("search", new SearchObject());
    modelAndView.addObject("reviewCount", reviewList.size());
    modelAndView.addObject("user", user);
    return modelAndView;
  }

  @GetMapping("/admin/")
  public ModelAndView adminIndexRoute(HttpSession session) {
    User user = session.getAttribute("user") == null ? new User() : (User) session.getAttribute("user");
    if(user.getId()==0 || user.getRole()!= Role.ADMIN)
    {
      return new ModelAndView("redirect:/admin/login");
    }
    ModelAndView modelAndView = new ModelAndView("admin_home");
    modelAndView.addObject("user", user);
    return modelAndView;
  }


  @GetMapping("/company")
  public String companyRoute() {
    return "company";
  }

}
