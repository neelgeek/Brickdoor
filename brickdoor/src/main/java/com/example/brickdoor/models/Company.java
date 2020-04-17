package com.example.brickdoor.models;

import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(referencedColumnName="id")
public class Company extends User {

  private String companyName;
  private String companyAddress;

  public Company() {super();}

  public Company(String companyName, String companyAddress) {
    this.companyName = companyName;
    this.companyAddress = companyAddress;
  }

  public Company(String firstName, String lastName, String username, String password,
      String email, String dob, Set<PhoneNumber> phoneNumbers, String companyName, String companyAddress) {
    super(firstName, lastName, username, password, email, dob, phoneNumbers, Role.COMPANY);
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
}
