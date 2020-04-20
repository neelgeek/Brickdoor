package com.example.brickdoor.daos;

import com.example.brickdoor.models.Badge;
import com.example.brickdoor.models.Company;
import com.example.brickdoor.models.InterviewReview;

import com.example.brickdoor.models.Review;
import com.example.brickdoor.models.Student;
import com.example.brickdoor.models.WorkReview;

import org.hibernate.jdbc.Work;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReviewDaoTest {

    @Autowired
    private ReviewDao reviewDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private BrickDoorDao brickDoorDao;

    private Company apple;
    private Company boeing;
    private Student alice;
    private Student bob;

    @Before
    public void setUp() throws Exception {
        brickDoorDao.truncateDatabase();

        // test phone number is null.
        alice = new Student("alice", "alice", "alice", "alice", "alice", "dob", null);
        bob = new Student("bob", "bob", "bob", "bob", "bob", "dob", null);

        apple = new Company("apple", "address");
        boeing = new Company("boeing", "address");

        userDao.registerUser(alice);
        userDao.registerUser(bob);
        userDao.registerUser(apple);
        userDao.registerUser(boeing);

        WorkReview aliceApple = new WorkReview(apple, alice, Badge.AWESOME, "awesome apple", "content", "swe");
        InterviewReview bobApple = new InterviewReview(apple, bob, Badge.MEDIOCRE, "mediocre apple", "content", "what is a coding monkey?");

        WorkReview aliceBoeing = new WorkReview(boeing, alice, Badge.AWESOME, "awesome boeing", "content", "Mechanical Engineer");
        InterviewReview bobBoeing = new InterviewReview(boeing, bob, Badge.MEDIOCRE, "mediocre boeing", "content", "How to built a boeing 777?");
        reviewDao.createReview(aliceApple);
        reviewDao.createReview(bobApple);

        reviewDao.createReview(aliceBoeing);
        reviewDao.createReview(bobBoeing);
    }

    @After
    public void afterEach() {
        brickDoorDao.truncateDatabase();
    }

    @Test
    public void findAllInterviewReview() {
        Assert.assertEquals(2, reviewDao.findAllInterviewReview().size());
    }

    @Test
    public void findAllWorkReview() {
        Assert.assertEquals(2, reviewDao.findAllWorkReview().size());
    }

    @Test
    public void findAllReview() {
        Assert.assertEquals(4, reviewDao.findAllReview().size());
    }

    @Test
    public void createReview() {
    }

    @Test
    public void testFindReviewByCompanyName() {
        List<Review> reviews = reviewDao.findReviewByCompanyName("apple");
        Assert.assertEquals(true, reviews.stream().anyMatch(x -> x.getTitle().equals("mediocre apple") && x.getReviewerName().equals("bob")));
        Assert.assertEquals(true, reviews.stream().anyMatch(x -> x.getTitle().equals("awesome apple") && x.getReviewerName().equals("alice")));
        Assert.assertEquals(2, reviews.size());
    }

    @Test
    public void testFindReviewByStudentName() {
        List<Review> reviews = reviewDao.findReviewByStudentUsername("bob");
        Assert.assertEquals(true, reviews.stream().anyMatch(x -> x.getTitle().equals("mediocre apple") && x.getReviewerName().equals("bob")));
        Assert.assertEquals(true, reviews.stream().anyMatch(x -> x.getTitle().equals("mediocre boeing") && x.getReviewerName().equals("bob")));
        Assert.assertEquals(2, reviews.size());
    }

    @Test
    public void testFindReviewByCompanyId() {
        List<Review> reviews = reviewDao.findReviewByCompanyId(boeing.getId());
        Assert.assertEquals(true, reviews.stream().anyMatch(x -> x.getTitle().equals("mediocre boeing") && x.getReviewerName().equals("bob")));
        Assert.assertEquals(true, reviews.stream().anyMatch(x -> x.getTitle().equals("awesome boeing") && x.getReviewerName().equals("alice")));
        Assert.assertEquals(2, reviews.size());
    }

    @Test
    public void testFindWorkReviewByCompanyId() {
        List<WorkReview> boeingWork = reviewDao.findWorkReviewsByCompanyId(boeing.getId());
        Assert.assertEquals(true, boeingWork.stream().anyMatch(x -> x.getTitle().equals("awesome boeing") && x.getReviewerName().equals("alice")));
        List<WorkReview> appleWork = reviewDao.findWorkReviewsByCompanyId(apple.getId());
        Assert.assertEquals(true, appleWork.stream().anyMatch(x -> x.getTitle().equals("awesome apple") && x.getReviewerName().equals("alice")));
        Assert.assertEquals(1, boeingWork.size());
        Assert.assertEquals(1, appleWork.size());
    }

    @Test
    public void testFindInterviewReviewByCompanyId() {
        List<InterviewReview> boeingInterview = reviewDao.findInterviewReviewsByCompanyId(this.boeing.getId());
        Assert.assertEquals(true, boeingInterview.stream().anyMatch(x -> x.getTitle().equals("mediocre boeing") && x.getReviewerName().equals("bob")));
        List<InterviewReview> appleInterview = reviewDao.findInterviewReviewsByCompanyId(this.apple.getId());
        Assert.assertEquals(true, appleInterview.stream().anyMatch(x -> x.getTitle().equals("mediocre apple") && x.getReviewerName().equals("bob")));
        Assert.assertEquals(1, boeingInterview.size());
        Assert.assertEquals(1, appleInterview.size());
    }

    @Test
    public void testFindReviewsByStudentId() {
        List<Review> reviews = reviewDao.findAllReviewsByStudentId(this.alice.getId());
        Assert.assertEquals(true, reviews.stream().anyMatch(x -> x.getTitle().equals("awesome boeing") && x.getReviewerName().equals("alice")));
        Assert.assertEquals(true, reviews.stream().anyMatch(x -> x.getTitle().equals("awesome apple") && x.getReviewerName().equals("alice")));
        Assert.assertEquals(2, reviews.size());
    }

    @Test
    public void testFindWorkReviewsByStudentId() {
        List<WorkReview> aliceReviews = reviewDao.findWorkReviewsReviewByStudentId(this.alice.getId());
        Assert.assertEquals(true, aliceReviews.stream().anyMatch(x -> x.getTitle().equals("awesome boeing") && x.getReviewerName().equals("alice")));
        Assert.assertEquals(true, aliceReviews.stream().anyMatch(x -> x.getTitle().equals("awesome apple") && x.getReviewerName().equals("alice")));
        Assert.assertEquals(2, aliceReviews.size());
        List<WorkReview> bobReviews = reviewDao.findWorkReviewsReviewByStudentId(this.bob.getId());
        Assert.assertEquals(0, bobReviews.size());
    }

    @Test
    public void testFindInterviewReviewsByStudentId() {
        List<InterviewReview> aliceReviews = reviewDao.findInterviewReviewsByStudentId(this.alice.getId());
        Assert.assertEquals(0, aliceReviews.size());
        List<InterviewReview> bobReviews = reviewDao.findInterviewReviewsByStudentId(this.bob.getId());
        Assert.assertEquals(true, bobReviews.stream().anyMatch(x -> x.getTitle().equals("mediocre boeing") && x.getReviewerName().equals("bob")));
        Assert.assertEquals(true, bobReviews.stream().anyMatch(x -> x.getTitle().equals("mediocre apple") && x.getReviewerName().equals("bob")));

        Assert.assertEquals(2, bobReviews.size());
    }


}