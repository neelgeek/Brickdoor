package com.example.brickdoor.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashSet;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "user")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private String username;
  private String password;
  private String email;
  private String dob;
  private Role role;

  @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
  private Set<PhoneNumber> phoneNumbers;


  @ManyToMany (fetch = FetchType.EAGER)
  @JoinTable(
      name = "follows",
      joinColumns = @JoinColumn(
          name="follower_id",
          referencedColumnName = "id"
      ),
      inverseJoinColumns = @JoinColumn(
          name="following_id",
          referencedColumnName = "id"
      ),
      uniqueConstraints = @UniqueConstraint(columnNames = {"follower_id", "following_id"})
  )
  private Set<User> following;

  @ManyToMany(mappedBy = "following", fetch = FetchType.EAGER)
  @JsonIgnore
  private Set<User> followers;


  public User() {
  }

  public User(Role role) {
    this.role = role;
  }

  public User(String username, String password, String email, String dob, Set<PhoneNumber> phoneNumbers, Role role) {
    this.username = username;
    this.password = password;
    this.email = email;
    this.dob = dob;
    this.phoneNumbers = phoneNumbers;
    this.role = role;
    this.following = new HashSet<>();
    this.followers = new HashSet<>();
  }

  public User(String username, String password, String email, String dob, Set<PhoneNumber> phoneNumbers, Role role,
      Set<User> following, Set<User> followers) {
    this.username = username;
    this.password = password;
    this.email = email;
    this.dob = dob;
    this.phoneNumbers = phoneNumbers;
    this.role = role;
    this.following = following;
    this.followers = followers;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
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

  public String getDob() {
    return dob;
  }

  public void setDob(String dob) {
    this.dob = dob;
  }

  public Set<PhoneNumber> getPhoneNumbers() {
    return phoneNumbers;
  }

  public void setPhoneNumbers(Set<PhoneNumber> phoneNumbers) {
    this.phoneNumbers.clear();
    this.phoneNumbers.addAll(phoneNumbers);
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }
  public Set<User> getFollowing() {
    return following;
  }

  public void setFollowing(Set<User> following) {
    this.following.clear();
    this.following.addAll(following);
  }

  public Set<User> getFollowers() {
    return followers;
  }

  public void setFollowers(Set<User> followers) {
    this.followers.clear();
    this.followers.addAll(followers);
  }

  public void addFollowing(User toFollow) {
    this.following.add(toFollow);
  }

  public void addFollower(User toFollow) {
    this.followers.add(toFollow);
  }

  public void removeFollowing(User toUnfollow) {
    this.following.remove(toUnfollow);
  }

  public void removeFollower(User toUnfollow) {
    this.followers.remove(toUnfollow);
  }
}
