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
    String interviewQuestion = "";

    @Column(name = "content")
    String content = "";

    public InterviewReview() {}

    public InterviewReview(String reviewerName, Badge badge) {
        super(reviewerName, badge);
    }

    public String getInterviewQuestion() {
        return interviewQuestion;
    }

    public void setInterviewQuestion(String interviewQuestion) {
        this.interviewQuestion = interviewQuestion;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
