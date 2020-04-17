package com.example.brickdoor.models;

import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(referencedColumnName="id")
public class Admin extends User {
  public Admin() {super();}

  public Admin(String firstName, String lastName, String username, String password,
      String email, String dob, Set<PhoneNumber> phoneNumbers) {
    super(firstName, lastName, username, password, email, dob, phoneNumbers, Role.ADMIN);
  }

  public Admin(String firstName, String lastName, String username, String password,
      String email, String dob, Set<PhoneNumber> phoneNumbers, Set<User> following, User followedBy) {
    super(firstName, lastName, username, password, email, dob, phoneNumbers, Role.ADMIN, following, followedBy);
  }
}
