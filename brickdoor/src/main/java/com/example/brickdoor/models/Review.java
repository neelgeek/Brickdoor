package com.example.brickdoor.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name = "review")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "reviewername")
    String reviewerName = "";

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    Badge badge = Badge.NOTAVAILABLE;

    public Review() {
    }

    public Review(String reviewerName, Badge badge) {
        this.reviewerName = reviewerName;
        this.badge = badge;
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
}
