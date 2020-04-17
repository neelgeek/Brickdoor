package com.example.brickdoor.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name = "workreview")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class WorkReview extends Review{

    @Column(name = "jobtitle")
    private String jobTitle = "";


    public WorkReview(){}

    public WorkReview(String reviewerName, Badge badge, String title, String content) {
        super(reviewerName, badge, title, content);
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

}
