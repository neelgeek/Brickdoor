package com.example.brickdoor.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(referencedColumnName = "id")
public class Student extends User {

  private String firstName;
  private String lastName;

  @JsonIgnore
  @OneToMany(mappedBy = "student", orphanRemoval=true)
  private List<Review> givenReviews;

  public Student() {
    super(Role.STUDENT);
  }

  public Student(String firstName, String lastName, String username, String password,
                 String email, String dob, Set<PhoneNumber> phoneNumbers) {
    super(username, password, email, dob, phoneNumbers, Role.STUDENT);
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public Student(String firstName, String lastName, String username, String password,
                 String email, String dob, Set<PhoneNumber> phoneNumbers, Set<User> following, Set<User> followers) {
    super(username, password, email, dob, phoneNumbers, Role.STUDENT,
            following, followers);
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public List<Review> getGivenReviews() {
    return givenReviews;
  }

  public void setGivenReviews(List<Review> givenReviews) {
    this.givenReviews = givenReviews;
  }
}
