package com.example.brickdoor.daos;

import com.example.brickdoor.models.Badge;
import com.example.brickdoor.models.Company;
import com.example.brickdoor.models.InterviewReview;

import com.example.brickdoor.models.Review;
import com.example.brickdoor.models.Student;
import com.example.brickdoor.models.WorkReview;

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

    @Before
    public void setUp() throws Exception {
        brickDoorDao.truncateDatabase();

        // test phone number is null.
        Student alice = new Student("alice", "alice", "alice", "alice", "alice", "dob", null);
        Student bob = new Student("bob", "bob", "bob", "bob", "bob", "dob", null);

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
        Assert.assertEquals(2, reviews.size());    }

}