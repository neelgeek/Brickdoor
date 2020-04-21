package com.example.brickdoor.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
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

    @Column(name = "badge")
    protected int badgeId = Badge.NOTAVAILABLE.getId();

    @Column(name = "title")
    protected String title = "";

    @Column(name = "content", length = 1500)
    protected String content = "";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "companyId")
    protected Company company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "studentId")
    protected Student student;

    public Review() {
    }

    public Review(String reviewerName, Badge badge, String title, String content) {
        this.reviewerName = reviewerName;
        this.badgeId = badge.getId();
        this.title = title;
        this.content = content;
    }

    public Review(Company company, Student student, Badge badge, String title, String content) {
        this.badgeId = badge.getId();
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

    public Badge getBadgeId() {
        return Badge.parse(this.badgeId);
    }

    public void setBadgeId(Badge badge) {
        this.badgeId = badge.getId();
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
