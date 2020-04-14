package com.example.brickdoor.models;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class WorkReviewTest {

    private WorkReview emptyReview;
    private WorkReview filled;
    private static final String reviewer = "reviewer";
    private static final Badge badge = Badge.GOOD;

    private static final String pros = "Good work environment.";
    private static final String cons = "None";
    private static final String jobTitle = "Assistant Manager";

    @Before
    public void setUp() {
        emptyReview = new WorkReview();
        filled = new WorkReview(reviewer, badge);
        filled.setCons(cons);
        filled.setPros(pros);
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

    @Test
    public void getPros() {
        Assert.assertEquals("", emptyReview.getPros());
        Assert.assertEquals(pros, filled.getPros());
    }

    @Test
    public void setPros() {
        emptyReview.setPros("pros");
        Assert.assertEquals("pros", emptyReview.getPros());
    }

    @Test
    public void getCons() {
        Assert.assertEquals("", emptyReview.getCons());
        Assert.assertEquals(cons, filled.getCons());
    }

    @Test
    public void setCons() {
        emptyReview.setCons("N/A");
        Assert.assertEquals("N/A", emptyReview.getCons());
    }
}