package com.example.brickdoor.models;

import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(referencedColumnName="id")
public class Admin extends User {
  public Admin() {super();}

  public Admin(String firstName, String lastName, String username, String password,
      String email) {
    super(firstName, lastName, username, password, email);
  }

  public Admin(String firstName, String lastName, String username, String password,
      String email, Set<User> following, User followedBy) {
    super(firstName, lastName, username, password, email, following, followedBy);
  }
}
