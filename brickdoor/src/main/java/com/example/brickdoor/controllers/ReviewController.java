package com.example.brickdoor.controllers;

import com.example.brickdoor.daos.ReviewDao;
import com.example.brickdoor.models.InterviewReview;
import com.example.brickdoor.models.Review;
import com.example.brickdoor.models.WorkReview;

import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ReviewController {

    @Autowired
    ReviewDao reviewDao;

    @PostMapping("/createInterviewReview")
    public Review createInterviewReview() {
        InterviewReview review = new InterviewReview();
        review.setContent("Good");
        review.setInterviewQuestion("asdf");
        return reviewDao.createReview(review);
    }

    @PostMapping("/createWorkReview")
    public Review createWorkReview() {
        WorkReview review = new WorkReview();
        review.setJobTitle("asdfee");
        return reviewDao.createReview(review);
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
