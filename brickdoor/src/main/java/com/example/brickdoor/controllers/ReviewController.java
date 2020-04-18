package com.example.brickdoor.controllers;

import com.example.brickdoor.daos.ReviewDao;
import com.example.brickdoor.models.InterviewReview;
import com.example.brickdoor.models.Review;
import com.example.brickdoor.models.User;
import com.example.brickdoor.models.WorkReview;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import javax.persistence.Column;
import javax.servlet.http.HttpSession;

@RestController
public class ReviewController {

  @Autowired
  ReviewDao reviewDao;

  @PostMapping("/interview_review")
  public String createInterviewReview(HttpSession session, @RequestBody InterviewReview review) {
    reviewDao.createReview(review);
    return "review created";

  }

  @GetMapping("/work_review")
  public ModelAndView WorkReviewGET(HttpSession session) {
    User user = session.getAttribute("user") == null ? new User() : (User) session.getAttribute("user");
    if (user.getId() == 0) {
      return new ModelAndView("redirect:/");
    }
    ModelAndView modelAndView = new ModelAndView("job_review");
    modelAndView.addObject("user", user);
    modelAndView.addObject("review", new work_review_form());
    return modelAndView;
  }

  @PostMapping(path = "/work_review")
  public ModelAndView WorkReviewPOST(HttpSession session, @ModelAttribute("review") work_review_form review) {
    System.out.println(review.getCompanyId());
    System.out.println(review.getJobTitle());
    System.out.println(review.getTitle());
    System.out.println(review.getContent());
    // Please use the above info to create a new Review object and save it using the DAO.
//    reviewDao.createReview(review);
    return new ModelAndView("redirect:/work_review");

  }

  @PostMapping("/updateInterviewReview")
  public String updateInterviewReview(HttpSession session, @RequestBody InterviewReview review) {
    InterviewReview updated = reviewDao.updateInterviewReview(review);
    if (updated != null) {
      return "review updated";
    } else {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

  }

  @PostMapping("/updateWorkReview")
  public String updateWorkReview(HttpSession session, @RequestBody WorkReview review) {
    WorkReview updated = reviewDao.updateWorkReview(review);
    if (updated != null) {
      return "review updated";
    } else {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
  }


  @GetMapping("/findAllReview")
  public List<Review> findAllReview() {
    return reviewDao.findAllReview();
  }

  @GetMapping("/findAllInterviewReview")
  public List<InterviewReview> findAllInterviewReview() {
    return reviewDao.findAllInterviewReview();
  }

  @GetMapping("/findAllWorkReview")
  public List<WorkReview> findAllWorkReview() {
    return reviewDao.findAllWorkReview();
  }

}

// This object is submitted by the user in post route to create a work review.
class work_review_form {


  private String title = ""; // Review title
  private String jobTitle = ""; // Job title
  private String content = ""; // Review Content
  private int companyId; // Company Id

  public String getTitle() {
    return title;
  }

  public String getJobTitle() {
    return jobTitle;
  }

  public String getContent() {
    return content;
  }

  public int getCompanyId() {
    return companyId;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setJobTitle(String jobTitle) {
    this.jobTitle = jobTitle;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public void setCompanyId(int companyId) {
    this.companyId = companyId;
  }
}
