package com.example.brickdoor.models;

import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(referencedColumnName="id")
public class Student extends User {

  public Student() {super();}

  public Student(String firstName, String lastName, String username, String password,
      String email) {
    super(firstName, lastName, username, password, email);
  }
}
