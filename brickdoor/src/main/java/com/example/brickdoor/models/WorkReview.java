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
    String jobTitle = "";

    String pros = "";

    String cons = "";

    public WorkReview(){}

    public WorkReview(String reviewerName, Badge badge) {
        super(reviewerName, badge);
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getPros() {
        return pros;
    }

    public void setPros(String pros) {
        this.pros = pros;
    }

    public String getCons() {
        return cons;
    }

    public void setCons(String cons) {
        this.cons = cons;
    }
}
