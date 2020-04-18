package com.example.brickdoor.models;

import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "student")
@PrimaryKeyJoinColumn(referencedColumnName="id")
public class Student extends User {

  private String firstName;
  private String lastName;

  public Student() {
    super();
  }

  public Student(String firstName, String lastName, String username, String password,
      String email, String dob, Set<PhoneNumber> phoneNumbers) {
    super(username, password, email, dob, phoneNumbers, Role.STUDENT);
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public Student(String firstName, String lastName, String username, String password,
      String email, String dob, Set<PhoneNumber> phoneNumbers, Set<User> following, User followedBy) {
    super(username, password, email, dob, phoneNumbers, Role.STUDENT,
        following, followedBy);
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

}
