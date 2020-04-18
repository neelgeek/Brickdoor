package com.example.brickdoor.models;

import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(referencedColumnName="id")
public class Admin extends User {

  private String firstName;
  private String lastName;

  public Admin() {super();}

  public Admin(String firstName, String lastName, String username, String password,
      String email, String dob, Set<PhoneNumber> phoneNumbers) {
    super(username, password, email, dob, phoneNumbers, Role.ADMIN);
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public Admin(String firstName, String lastName, String username, String password,
      String email, String dob, Set<PhoneNumber> phoneNumbers, Set<User> following, User followedBy) {
    super(username, password, email, dob, phoneNumbers, Role.ADMIN, following, followedBy);
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
