package com.example.brickdoor.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name = "interviewreview")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class InterviewReview extends  Review{

    @Column(name = "interviewquestion")
    private String interviewQuestion = "";

    public InterviewReview() {}

    public InterviewReview(String reviewerName, Badge badge, String title, String content) {
        super(reviewerName, badge, title, content);
    }

    public String getInterviewQuestion() {
        return interviewQuestion;
    }

    public void setInterviewQuestion(String interviewQuestion) {
        this.interviewQuestion = interviewQuestion;
    }

}
