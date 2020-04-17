package com.example.brickdoor.models;

import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "user")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private String firstName;
  private String lastName;
  private String username;
  private String password;
  private String email;
  private String dob;
  private List<String> phoneNumbers;
  private Role role;

  @OneToMany(mappedBy = "followedBy", fetch = FetchType.LAZY)
  private Set<User> following;

  @JoinColumn(name = "following_id", referencedColumnName = "id")
  @ManyToOne(fetch = FetchType.LAZY)
  private User followedBy;

  public User() {
  }

  public User(String firstName, String lastName, String username, String password,
              String email, String dob, List<String> phoneNumbers, Role role) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.username = username;
    this.password = password;
    this.email = email;
    this.dob = dob;
    this.phoneNumbers = phoneNumbers;
    this.role = role;
  }

  public User(String firstName, String lastName, String username, String password,
              String email, String dob, List<String> phoneNumbers, Role role, Set<User> following,
              User followedBy) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.username = username;
    this.password = password;
    this.email = email;
    this.dob = dob;
    this.phoneNumbers = phoneNumbers;
    this.role = role;
    this.following = following;
    this.followedBy = followedBy;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
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

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Set<User> getFollowing() {
    return following;
  }

  public void setFollowing(Set<User> following) {
    this.following = following;
  }

  public User getFollowedBy() {
    return followedBy;
  }

  public void setFollowedBy(User followedBy) {
    this.followedBy = followedBy;
  }

  public String getDob() {
    return dob;
  }

  public void setDob(String dob) {
    this.dob = dob;
  }

  public List<String> getPhoneNumbers() {
    return phoneNumbers;
  }

  public void setPhoneNumbers(List<String> phoneNumbers) {
    this.phoneNumbers = phoneNumbers;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }
}
