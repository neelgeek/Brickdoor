package com.example.brickdoor.models;

import org.hibernate.jdbc.Work;

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

    public WorkReview(Company company, Student student, Badge badge, String title, String content, String jobTitle) {
        super(company, student, badge, title, content);
        this.jobTitle = jobTitle;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

}
