package com.example.brickdoor.controllers;

import com.example.brickdoor.daos.ReviewDao;
import com.example.brickdoor.daos.UserDao;
import com.example.brickdoor.models.Badge;
import com.example.brickdoor.models.Company;
import com.example.brickdoor.models.InterviewReview;
import com.example.brickdoor.models.Review;
import com.example.brickdoor.models.Role;
import com.example.brickdoor.models.Student;
import com.example.brickdoor.models.User;
import com.example.brickdoor.models.WorkReview;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import javax.servlet.http.HttpSession;

@Controller
public class ReviewController {

  @Autowired
  ReviewDao reviewDao;

  @Autowired
  UserDao userDao;

  @GetMapping("/interview_review")
  public ModelAndView createInterviewReviewGET(HttpSession session) {
    User user = session.getAttribute("user") == null ? new User() : (User) session.getAttribute("user");
    if (user.getId() == 0) {
      return new ModelAndView("redirect:/");
    }
    List<Company> companies = userDao.getAllCompanies();
    ModelAndView modelAndView = new ModelAndView("interview_review");
    modelAndView.addObject("user", user);
    modelAndView.addObject("review", new interview_review_form());
    modelAndView.addObject("companies",companies);
    System.out.println("company" + companies.get(0).getCompanyName());
    return modelAndView;


  }

  @PostMapping("/interview_review")
  public ModelAndView createInterviewReviewPOST(HttpSession session, @ModelAttribute("review") interview_review_form review) {

    System.out.println(review.getCompanyId());
    System.out.println(review.getTitle());
    System.out.println(review.getContent());
    System.out.println(review.getQuestions());

    // Please use the above info to create a new Review object and save it using the DAO.
    Company company = userDao.findCompanyById(review.getCompanyId());
    User user = (User) session.getAttribute("user");
    Student student = userDao.findStudentById(user.getId());
    if (company == null || student == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
    InterviewReview fromReviewForm = new InterviewReview(company, student,
            Badge.NOTAVAILABLE, review.getTitle(),
            review.getContent(), review.getQuestions());
    reviewDao.createReview(fromReviewForm);
    return new ModelAndView("redirect:/interview_review");

  }

  @GetMapping("/work_review")
  public ModelAndView WorkReviewGET(HttpSession session) {
    User user = session.getAttribute("user") == null ? new User() : (User) session.getAttribute("user");
    if (user.getId() == 0) {
      return new ModelAndView("redirect:/");
    }
    ModelAndView modelAndView = new ModelAndView("job_review");
    List<Company> companies = userDao.getAllCompanies();
    modelAndView.addObject("companies",companies);
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
    Company company = userDao.findCompanyById(review.getCompanyId());
    User user = (User) session.getAttribute("user");
    Student student = userDao.findStudentById(user.getId());
    if (company == null || student == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
    // TODO: review form include badge, then change following code.
    WorkReview fromReviewForm = new WorkReview(company, student,
            Badge.NOTAVAILABLE, review.getTitle(),
            review.getContent(), review.getJobTitle());
    reviewDao.createReview(fromReviewForm);
    return new ModelAndView("redirect:/work_review");

  }

  @PostMapping("/updateInterviewReview")
  public ModelAndView updateInterviewReview(HttpSession session, @ModelAttribute("review") InterviewReview review) {
    InterviewReview updated = reviewDao.updateInterviewReview(review);
    if (updated != null) {
      return new ModelAndView("redirect:/admin/manage/reviews/interview");
    } else {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

  }

  @PostMapping("/updateWorkReview")
  public ModelAndView updateWorkReview(HttpSession session, @ModelAttribute("review") WorkReview review) {
    WorkReview updated = reviewDao.updateWorkReview(review);
    if (updated != null) {
      return new ModelAndView("redirect:/admin/manage/reviews/work");
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

  @GetMapping("/deleteReview/{wId}")
  public ModelAndView deleteWorkReview(HttpSession session, @PathVariable("wId") Integer wId){
    User user = (User) session.getAttribute("user");
    int userId = user.getId();
    if (userDao.getRole(userId) != Role.ADMIN) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }
    if (!reviewDao.deleteReviewById(wId)) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
    return new ModelAndView("redirect:/admin/");
  }
}

// This object is submitted by the user in post route to create a work review.
class work_review_form {

  private Integer id;
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

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
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

// This object is submitted by the user in post route to create a work review.
class interview_review_form {

  private Integer id;
  private String title = ""; // Review title
  private String questions = ""; // Interview questions
  private String content = ""; // Review Content
  private int companyId; // Company Id

  public String getTitle() {
    return title;
  }

  public String getQuestions() {
    return questions;
  }

  public String getContent() {
    return content;
  }

  public int getCompanyId() {
    return companyId;
  }

  public Integer getId() {
    return id;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setQuestions(String questions) {
    this.questions = questions;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public void setCompanyId(int companyId) {
    this.companyId = companyId;
  }

  public void setId(Integer id) {
    this.id = id;
  }
}
