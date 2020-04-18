package com.example.brickdoor.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "review")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "reviewername")
    protected String reviewerName = "";

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    protected Badge badge = Badge.NOTAVAILABLE;

    @Column(name = "title")
    protected String title = "";

    @Column(name = "content")
    protected String content = "";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "companyId")
    protected Company company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "studentId")
    protected Student student;

    public Review() {
    }

    public Review(String reviewerName, Badge badge, String title, String content) {
        this.reviewerName = reviewerName;
        this.badge = badge;
        this.title = title;
        this.content = content;
    }

    public Review(Company company, Student student, Badge badge, String title, String content) {
        this.badge = badge;
        this.title = title;
        this.content = content;
        this.company = company;
        this.student = student;
        this.reviewerName = student.getUsername();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReviewerName() {
        return reviewerName;
    }

    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }

    public Badge getBadge() {
        return badge;
    }

    public void setBadge(Badge badge) {
        this.badge = badge;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
