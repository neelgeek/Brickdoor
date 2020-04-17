package com.example.brickdoor.models;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class WorkReviewTest {

    private WorkReview emptyReview;
    private WorkReview filled;
    private static final String reviewer = "reviewer";
    private static final Badge badge = Badge.GOOD;

    private static final String content = "Good work environment.";
    private static final String jobTitle = "Assistant Manager";

    @Before
    public void setUp() {
        emptyReview = new WorkReview();
        filled = new WorkReview(reviewer, badge, "", content);
        filled.setJobTitle(jobTitle);
    }

    @Test
    public void getJobTitle() {
        Assert.assertEquals("", emptyReview.getJobTitle());
        Assert.assertEquals(jobTitle, filled.getJobTitle());
    }

    @Test
    public void setJobTitle() {
        emptyReview.setJobTitle("SWE");
        Assert.assertEquals("SWE", emptyReview.getJobTitle());
    }

}