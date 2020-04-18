package com.example.brickdoor.models;

import java.util.List;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(referencedColumnName="id")
public class Company extends User {

  private String companyName;
  private String companyAddress;

  @OneToMany(mappedBy = "company")
  private List<Review> receivedReviews;

  public Company() {super();}

  public Company(String companyName, String companyAddress) {
    super(Role.COMPANY);
    this.companyName = companyName;
    this.companyAddress = companyAddress;
  }

  public Company(String username, String password, String email, String dob, Set<PhoneNumber> phoneNumbers, String companyName, String companyAddress) {
    super(username, password, email, dob, phoneNumbers, Role.COMPANY);
    this.companyName = companyName;
    this.companyAddress = companyAddress;
  }

  public String getCompanyName() {
    return companyName;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

  public String getCompanyAddress() {
    return companyAddress;
  }

  public void setCompanyAddress(String companyAddress) {
    this.companyAddress = companyAddress;
  }

  public List<Review> getReceivedReviews() {
    return receivedReviews;
  }

  public void setReceivedReviews(List<Review> receivedReviews) {
    this.receivedReviews = receivedReviews;
  }
}
