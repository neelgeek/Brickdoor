package com.example.brickdoor.models;

import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "student")
public class Student extends User {

  public Student() {
    super();
  }

  public Student(String firstName, String lastName, String username, String password,
      String email, String dob, Set<PhoneNumber> phoneNumbers) {
    super(firstName, lastName, username, password, email, dob, phoneNumbers, Role.STUDENT);
  }

  public Student(String firstName, String lastName, String username, String password,
      String email, String dob, Set<PhoneNumber> phoneNumbers, Set<User> following, User followedBy) {
    super(firstName, lastName, username, password, email, dob, phoneNumbers, Role.STUDENT,
        following, followedBy);
  }

}
