package com.example.brickdoor.daos;

import com.example.brickdoor.models.InterviewReview;
import com.example.brickdoor.models.Review;
import com.example.brickdoor.models.WorkReview;
import com.example.brickdoor.repositories.InterviewReviewRepository;
import com.example.brickdoor.repositories.ReviewRepository;
import com.example.brickdoor.repositories.WorkReviewRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewDao {

    @Autowired
    private InterviewReviewRepository interviewReviewRepo;

    @Autowired
    private WorkReviewRepository workReviewRepo;

    @Autowired
    private ReviewRepository reviewRepo;

    public List<InterviewReview> findAllInterviewReview() {
        return interviewReviewRepo.findAllInterviewReview();
    }

    public List<WorkReview> findAllWorkReview() {
        return workReviewRepo.findAllWorkReview();
    }

    public List<Review> findAllReview() {
        return reviewRepo.findAllReview();
    }


    public Review createReview(Review review) {

        return reviewRepo.save(review);
    }


    public InterviewReview updateInterviewReview(InterviewReview newReview) {
        InterviewReview oldReview = interviewReviewRepo.findById(newReview.getId()).orElse(null);
        if (oldReview != null) {
            oldReview.setContent(newReview.getContent());
            oldReview.setBadgeId(newReview.getBadgeId());
            oldReview.setTitle(newReview.getTitle());
            oldReview.setInterviewQuestion(newReview.getInterviewQuestion());
            return reviewRepo.save(oldReview);
        }
        return null;
    }
    
    public WorkReview updateWorkReview(WorkReview newReview) {
        WorkReview oldReview = workReviewRepo.findById(newReview.getId()).orElse(null);
        if (oldReview != null) {
            oldReview.setContent(newReview.getContent());
            oldReview.setBadgeId(newReview.getBadgeId());
            oldReview.setTitle(newReview.getTitle());
            oldReview.setJobTitle(newReview.getJobTitle());
            return reviewRepo.save(oldReview);
        }
        return null;
    }

    public List<Review> findReviewByCompanyName(String companyName) {
        return reviewRepo.findReviewByCompanyName(companyName);
    }

    public List<Review> findReviewByCompanyId(int companyId) {
        return reviewRepo.findReviewByCompanyId(companyId);
    }

    public List<InterviewReview> findInterviewReviewsByCompanyId(int companyId) {
        return interviewReviewRepo.findInterviewReviewsByCompanyId(companyId);
    }

    public List<WorkReview> findWorkReviewsByCompanyId(int companyId) {
        return workReviewRepo.findWorkReviewsByCompanyId(companyId);
    }


    public List<Review> findReviewByStudentUsername(String username) {
        return reviewRepo.findReviewByUsername(username);
    }

    public List<Review> findAllReviewsByStudentId(int studentId) {
        return reviewRepo.findReviewByStudentId(studentId);
    }

    public List<Review> findInterviewReviewsByStudentId(int studentId) {
        return interviewReviewRepo.findInterviewReviewsByStudentId(studentId);
    }

    public List<Review> findWorkReviewsReviewByStudentId(int studentId) {
        return workReviewRepo.findWorkReviewByStudentId(studentId);
    }
}
