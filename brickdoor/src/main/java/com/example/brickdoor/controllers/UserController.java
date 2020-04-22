package com.example.brickdoor.controllers;

import com.example.brickdoor.daos.ReviewDao;
import com.example.brickdoor.daos.UserDao;
import com.example.brickdoor.models.Admin;
import com.example.brickdoor.models.Company;
import com.example.brickdoor.models.InterviewReview;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

@Controller()
public class UserController {

  @Autowired
  private UserDao userDao;

  @Autowired
  private ReviewDao reviewDao;

  // This the get route, do not edit this.
  @GetMapping("/login")
  public ModelAndView loginRouteGet(HttpSession session) {
    User user = session.getAttribute("user") == null ? new User() : (User) session.getAttribute("user");
    if (user.getId() != 0) {
      return new ModelAndView("redirect:/");
    }
    ModelAndView model = new ModelAndView("login");
    model.addObject("user", user);
    return model;
  }


  // Post route for login, handle user authentication here
  @PostMapping("/login")
  public ModelAndView loginRoutePost(HttpSession session, @ModelAttribute("user") User user) {
    String username = user.getUsername();
    String password = user.getPassword();
    User authenticatedStudent = userDao.authenticate(username, password, Role.STUDENT);
    User authenticatedCompany = userDao.authenticate(username, password, Role.COMPANY);
    if (authenticatedStudent == null && authenticatedCompany == null) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }
    if (authenticatedStudent != null) {
      session.setAttribute("user", authenticatedStudent);
    } else if (authenticatedCompany != null) {
      session.setAttribute("user", authenticatedCompany);
    }

    return new ModelAndView("redirect:/");
  }

  @GetMapping("/logout")
  public ModelAndView logoutRouteGet(HttpSession session) {
    session.invalidate();
    return new ModelAndView("redirect:/");
  }

  // This the get route, do not edit this.
  @GetMapping("/register")
  public ModelAndView registerRouteGet(HttpSession session) {
    Student user = session.getAttribute("user") == null ? new Student() : (Student) session.getAttribute("user");
    if (user.getId() != 0) {
      return new ModelAndView("redirect:/login");
    }
    ModelAndView model = new ModelAndView("register");
    model.addObject("user", user);
    return model;
  }

  // Post route for login, handle user authentication here
  @PostMapping("/registerStudent")
  public ModelAndView registerStudentPost(HttpSession session, @ModelAttribute("student") Student student) {
    if (student == null || student.getUsername() == null || student.getPassword() == null || student.getEmail() == null) {
      throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Missing Register Credentials");
    }


    if (userDao.registerUser(student) == null) {
      throw new ResponseStatusException(HttpStatus.CONFLICT);
    }

    Admin admin = session.getAttribute("user") == null ? new Admin() : (Admin) session.getAttribute("user");
    if (admin.getId() == 0) {
      return new ModelAndView("redirect:/login");
    }
    return new ModelAndView("redirect:/admin/manage/users");
  }

  // Post route for login, handle user authentication here
  @PostMapping("/registerCompany")
  public ModelAndView registerCompanyPost(HttpSession session, @ModelAttribute("company") Company company) {
    if (company == null || company.getUsername() == null || company.getPassword() == null || company.getEmail() == null) {
      throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Missing Register Credentials");
    }
    if (userDao.registerUser(company) == null) {
      throw new ResponseStatusException(HttpStatus.CONFLICT);
    }
    Admin admin = session.getAttribute("user") == null ? new Admin() : (Admin) session.getAttribute("user");
    if (admin.getId() == 0) {
      return new ModelAndView("redirect:/login");
    }
    return new ModelAndView("redirect:/admin/manage/companies");

  }

  @PostMapping("/registerAdmin")
  public String registerAdminPost(@ModelAttribute("admin") Admin admin) {
    if (admin == null || admin.getUsername() == null || admin.getPassword() == null || admin.getEmail() == null) {
      throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Missing Register Credentials");
    }
    if (userDao.registerUser(admin) == null) {
      throw new ResponseStatusException(HttpStatus.CONFLICT);
    }
    return "registered admin";
  }

  @PostMapping("/updateStudent")
  public ModelAndView updateStudent(HttpSession session, @ModelAttribute("student") Student student) {
    User user = (User) session.getAttribute("user");
    int userId = user.getId();
    Role userRole = userDao.getRole(userId);

//    boolean permissionRoles = userRole == Role.STUDENT || userRole == Role.ADMIN;
//    if (!permissionRoles) {
//      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
//    }
    System.out.println(student.getId());
    User updateUser = userDao.updateStudent(student.getId(), student);
    if (updateUser == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
    return new ModelAndView("redirect:/admin/manage/users");

  }

  @PostMapping("/updateCompany")
  public ModelAndView updateCompany(HttpSession session, @ModelAttribute("company") Company company) {
    User user = (User) session.getAttribute("user");
    int userId = user.getId();
    Role userRole = userDao.getRole(userId);

    System.out.println("Company Id is " + company.getId());
    User updateUser = userDao.updateCompany(company.getId(), company);
    if (updateUser == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
    return new ModelAndView("redirect:/admin/manage/companies");


  }

  @PutMapping("/updateAdmin")
  public String updateAdmin(HttpSession session, @ModelAttribute("admin") Admin admin) {
    User user = (User) session.getAttribute("user");
    int userId = user.getId();
    Role userRole = userDao.getRole(userId);
    if (userId == admin.getId() || userRole == Role.ADMIN) {
      User updateUser = userDao.updateAdmin(userId, admin);
      if (updateUser == null) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
      }
    } else {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }
    return "updated admin";
  }

  @GetMapping("/deleteUser/{uid}")
  public ModelAndView deleteUser(HttpSession session, @PathVariable("uid") Integer uid) {
    User user = (User) session.getAttribute("user");
    int userId = user.getId();
    if (userDao.getRole(userId) != Role.ADMIN) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }
    if (!userDao.deleteUser(uid)) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
    return new ModelAndView("redirect:/admin/");
  }

  @GetMapping("/getAllCompanies")
  public List<Company> getAllCompanies() {
    return userDao.getAllCompanies();
  }

  @GetMapping("/getAllStudents")
  public List<Student> getAllStudents() {
    return userDao.getAllStudents();
  }

  @GetMapping("/getAllAdmin")
  public List<Admin> getAllAdmin() {
    return userDao.getAllAdmin();
  }

  @PostMapping("/search")
  public ModelAndView searchCompanies(HttpSession session, @ModelAttribute("search") SearchObject search) {
    Student user = session.getAttribute("user") == null ? new Student() : (Student) session.getAttribute("user");
    Set<Company> matchedCompanies = userDao.searchCompanies(search.getQuery());
    System.out.println(matchedCompanies.size());
    ModelAndView model = new ModelAndView("search");
    model.addObject("user", user);
    model.addObject("query", search.getQuery());
    model.addObject("results", matchedCompanies);
    return model;
  }

  @PostMapping("/searchStudents")
  public ModelAndView searchStudents(HttpSession session, @ModelAttribute("search") SearchObject search) {
    Student user = session.getAttribute("user") == null ? new Student() : (Student) session.getAttribute("user");
    if (user.getId() == 0) {
      return new ModelAndView("redirect:/login");
    }
    Set<Student> matchedStudents = userDao.searchStudents(search.getQuery());

    ModelAndView model = new ModelAndView("search");
    model.addObject("user", user);
    model.addObject("query", search.getQuery());
    model.addObject("queryResult", matchedStudents);
    return model;
  }

  @PostMapping("/searchAdmin")
  public ModelAndView searchAdmin(HttpSession session, @ModelAttribute("search") SearchObject search) {
    Student user = session.getAttribute("user") == null ? new Student() : (Student) session.getAttribute("user");
    if (user.getId() == 0) {
      return new ModelAndView("redirect:/login");
    }
    Set<Admin> matchedAdmin = userDao.searchAdmin(search.getQuery());

    ModelAndView model = new ModelAndView("search");
    model.addObject("user", user);
    model.addObject("query", search.getQuery());
    model.addObject("queryResult", matchedAdmin);
    return model;
  }

  @GetMapping("/profile")
  public ModelAndView userProfile(HttpSession session) {
    Student user = session.getAttribute("user") == null ? new Student() : (Student) session.getAttribute("user");
    if (user.getId() == 0) {
      return new ModelAndView("redirect:/login");
    }

    List<InterviewReview> interviews = reviewDao.findInterviewReviewsByStudentId(user.getId());
    List<WorkReview> workReviews = reviewDao.findWorkReviewsReviewByStudentId(user.getId());

    ModelAndView model = new ModelAndView("profile");
    model.addObject("user", user);
    model.addObject("interviews", interviews);
    model.addObject("works", workReviews);
    return model;
  }

  @GetMapping("/user/{userId}")
  public ModelAndView userProfileGET(HttpSession session, @PathVariable("userId") Integer userId) {
    Student user = session.getAttribute("user") == null ? new Student() : (Student) session.getAttribute("user");
    Student person = userDao.findStudentById(userId);
    if (person == null) {
      return new ModelAndView("redirect:/");
    }
    List<InterviewReview> interviews = reviewDao.findInterviewReviewsByStudentId(userId);
    List<WorkReview> workReviews = reviewDao.findWorkReviewsReviewByStudentId(userId);

    ModelAndView model = new ModelAndView("user");
    model.addObject("user", user);
    model.addObject("person", person);
    model.addObject("interviews", interviews);
    model.addObject("works", workReviews);
    return model;
  }

  @GetMapping("/company/{companyId}")
  public ModelAndView companyProfileGET(HttpSession session, @PathVariable("companyId") Integer companyId) {
    User loggedInUser = session.getAttribute("user") == null ? new User() : (User) session.getAttribute("user");
    if(loggedInUser.getId()==0){
      return new ModelAndView("redirect:/login");
    }
    Student user = new Student();

    if(loggedInUser.getRole()==Role.COMPANY){
      Company currentCompany = userDao.findCompanyById(loggedInUser.getId());
      user.setId(loggedInUser.getId());
      user.setFirstName(currentCompany.getCompanyName());
      user.setLastName("");
      user.setRole(loggedInUser.getRole());
    }
    else{
      user = userDao.findStudentById(loggedInUser.getId());
    }

    Company company = userDao.findCompanyById(companyId);
    if (company == null) {
      return new ModelAndView("redirect:/");
    }
    List<InterviewReview> interviews = reviewDao.findInterviewReviewsByCompanyId(companyId);
    List<WorkReview> workReviews = reviewDao.findWorkReviewsByCompanyId(companyId);

    ModelAndView model = new ModelAndView("company");
    model.addObject("company", company.getCompanyName());
    model.addObject("user", user);
    model.addObject("interviews", interviews);
    model.addObject("works", workReviews);
    return model;
  }

  @PutMapping("/followUser/{userId}")
  public ModelAndView followUser(HttpSession session, @PathVariable("userId") Integer userId) {
    Student user = session.getAttribute("user") == null ? new Student() : (Student) session.getAttribute("user");
    User toFollow = userDao.findById(userId);
    if (toFollow == null) {
      return new ModelAndView("redirect:/");
    }
    userDao.follow(user, toFollow);
    ModelAndView model = new ModelAndView("user");
    model.addObject("user", user);
    model.addObject("toFollow", toFollow);
    return model;
  }

  @PutMapping("/unfollowUser/{userId}")
  public ModelAndView unfollowUser(HttpSession session, @PathVariable("userId") Integer userId) {
    Student user = session.getAttribute("user") == null ? new Student() : (Student) session.getAttribute("user");
    User toUnfollow = userDao.findById(userId);
    if (toUnfollow == null) {
      return new ModelAndView("redirect:/");
    }
    userDao.unfollow(user, toUnfollow);
    ModelAndView model = new ModelAndView("user");
    model.addObject("user", user);
    model.addObject("toUnfollow", toUnfollow);
    return model;
  }

  @GetMapping("/getFollowers")
  public ModelAndView getFollowers(HttpSession session) {
    Student user = session.getAttribute("user") == null ? new Student() : (Student) session.getAttribute("user");
    Set<User> followers = userDao.getFollowers(user.getId());
    ModelAndView model = new ModelAndView("user");
    model.addObject("user", user);
    model.addObject("followers", followers);
    return model;
  }

  @GetMapping("/getFollowing")
  public ModelAndView getFollowing(HttpSession session) {
    Student user = session.getAttribute("user") == null ? new Student() : (Student) session.getAttribute("user");
    Set<User> following = userDao.getFollowing(user.getId());
    ModelAndView model = new ModelAndView("user");
    model.addObject("user", user);
    model.addObject("following", following);
    return model;
  }

  @GetMapping("/getFollowingStudents")
  public ModelAndView getFollowingStudents(HttpSession session) {
    Student user = session.getAttribute("user") == null ? new Student() : (Student) session.getAttribute("user");
    Set<Student> followingStudents = userDao.getFollowingStudents(user.getId());
    ModelAndView model = new ModelAndView("user");
    model.addObject("user", user);
    model.addObject("followingStudents", followingStudents);
    return model;
  }

  @GetMapping("/getFollowingCompanies")
  public ModelAndView getFollowingCompanies(HttpSession session) {
    Student user = session.getAttribute("user") == null ? new Student() : (Student) session.getAttribute("user");
    Set<Company> followingCompanies = userDao.getFollowingCompanies(user.getId());
    ModelAndView model = new ModelAndView("user");
    model.addObject("user", user);
    model.addObject("followingCompanies", followingCompanies);
    return model;
  }

  @PostMapping("/updateUserRole/student")
  public ModelAndView updateUserRole(HttpSession session, @ModelAttribute("student") Student student) {
    User currUser = (User) session.getAttribute("user");
    int userId = currUser.getId();
    Role userRole = userDao.getRole(userId);
    if (userRole != Role.ADMIN) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }
    userDao.deleteUser(student.getId());
    User updatedUser = userDao.updateUserRole(student, Role.STUDENT);
    if (updatedUser == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
    ModelAndView model = new ModelAndView("user");
    return new ModelAndView("redirect:/admin/manage/users");

  }

  @PostMapping("/updateUserRole/company")
  public ModelAndView updateUserRole(HttpSession session, @ModelAttribute("company") Company company) {
    User currUser = (User) session.getAttribute("user");
    int userId = currUser.getId();
    Role userRole = userDao.getRole(userId);
    if (userRole != Role.ADMIN) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }
    userDao.deleteUser(company.getId());
    User updatedUser = userDao.updateUserRole(company, Role.COMPANY);
    if (updatedUser == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
    ModelAndView model = new ModelAndView("user");
    return new ModelAndView("redirect:/admin/manage/users");

  }

  @PostMapping("/updateUserRole/admin")
  public ModelAndView updateUserRole(HttpSession session, @ModelAttribute("admin") Admin admin) {
    User currUser = (User) session.getAttribute("user");
    int userId = currUser.getId();
    Role userRole = userDao.getRole(userId);
    if (userRole != Role.ADMIN) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }
    userDao.deleteUser(admin.getId());
    User updatedUser = userDao.updateUserRole(admin, Role.ADMIN);
    if (updatedUser == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    return new ModelAndView("redirect:/admin/manage/users");
  }

  @GetMapping("/updateprofile")
  public ModelAndView updateprofileGet(HttpSession session) {
    User user = session.getAttribute("user") == null ? new User() : (User) session.getAttribute("user");
    if (user.getId() == 0 || user.getRole() == Role.ADMIN) {
      return new ModelAndView("redirect:/login");
    }
    ModelAndView model;
    if(user.getRole()==Role.STUDENT){
      model= new ModelAndView("update_student");
    }
    else{
      Company currentCompany = userDao.findCompanyById(user.getId());
      Student student = new Student();
      student.setId(currentCompany.getId());
      student.setFirstName(currentCompany.getCompanyName());
      student.setLastName("");
      user=student;
      model= new ModelAndView("update_company");
      model.addObject("company",currentCompany);

    }


    model.addObject("user", user);
    model.addObject("person", user);
    return model;
  }

}

// Used to store the search query from the search bar.
class SearchObject {
  private String query;

  public String getQuery() {
    return query;
  }

  public void setQuery(String query) {
    this.query = query;
  }
}
