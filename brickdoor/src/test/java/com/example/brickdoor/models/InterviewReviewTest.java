package com.example.brickdoor.models;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class InterviewReviewTest {

    private InterviewReview emptyReview;
    private InterviewReview filled;
    private static final String reviewer = "reviewer";
    private static final String content = "Good work environment.";
    private static final Badge badge = Badge.GOOD;
    private static final String question = "What is your fav project?";

    @Before
    public void setUp() {
        emptyReview = new InterviewReview();
        filled = new InterviewReview(reviewer, badge);
        filled.setInterviewQuestion(question);
        filled.setContent(content);
    }

    @Test
    public void getReviewerName() {
        Assert.assertEquals("", emptyReview.getReviewerName());
        Assert.assertEquals(reviewer, filled.getReviewerName());
    }

    @Test
    public void setReviewerName() {
        emptyReview.setReviewerName("alice");
        Assert.assertEquals("alice", emptyReview.getReviewerName());
    }

    @Test
    public void getContent() {
        Assert.assertEquals(content, filled.getContent());
        Assert.assertEquals("", emptyReview.getContent());
    }

    @Test
    public void setContent() {
        emptyReview.setContent("content");
        Assert.assertEquals("content", emptyReview.getContent());
    }

    @Test
    public void getBadge() {
        Assert.assertEquals(badge, filled.getBadge());
        Assert.assertEquals(Badge.NOTAVAILABLE, emptyReview.getBadge());
    }

    @Test
    public void setBadge() {
        emptyReview.setBadge(Badge.AWESOME);
        Assert.assertEquals(Badge.AWESOME, emptyReview.getBadge());
    }

    @Test
    public void getInterviewQuestion() {
        Assert.assertEquals(question, filled.getInterviewQuestion());
        Assert.assertEquals("", emptyReview.getInterviewQuestion());
    }

    @Test
    public void setInterviewQuestion() {
        emptyReview.setInterviewQuestion("question");
        Assert.assertEquals("question", emptyReview.getInterviewQuestion());
    }
}