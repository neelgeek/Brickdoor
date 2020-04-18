package com.example.brickdoor.controllers;

import com.example.brickdoor.daos.ReviewDao;
import com.example.brickdoor.models.InterviewReview;
import com.example.brickdoor.models.Review;
import com.example.brickdoor.models.WorkReview;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import javax.servlet.http.HttpSession;

@RestController
public class ReviewController {

    @Autowired
    ReviewDao reviewDao;

    @PostMapping("/createInterviewReview")
    public String createInterviewReview(HttpSession session, @RequestBody InterviewReview review) {
        reviewDao.createReview(review);
        return "review created";

    }

    @PostMapping("/createWorkReview")
    public String createWorkReview(HttpSession session, @RequestBody WorkReview review) {
        reviewDao.createReview(review);
        return "review created";
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
