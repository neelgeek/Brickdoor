package com.example.brickdoor.daos;

import com.example.brickdoor.models.Company;
import com.example.brickdoor.models.Role;
import com.example.brickdoor.models.Student;
import com.example.brickdoor.models.User;
import com.example.brickdoor.repositories.CompanyRepository;
import com.example.brickdoor.repositories.StudentRepository;
import com.example.brickdoor.repositories.UserRepository;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserDao {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private StudentRepository studentRepository;

  @Autowired
  private CompanyRepository companyRepository;

  public User authenticate(String username, String password) {
    return userRepository.findUserByUserCredentials(username, password);
  }

  public boolean registerUser(User user) {
    if (userRepository.findUserByUsername(user.getUsername()) == null
        && userRepository.findUserByEmail(user.getEmail()) == null) {
      userRepository.save(user);
      return true;
    }
    return false;
  }

  public User updateStudent(int userId, Student updatedStudent) {
    Student outdatedStudent = studentRepository.findStudentById(userId);
    if (outdatedStudent != null) {
      updateBasicUserCred(outdatedStudent, updatedStudent);
      userRepository.save(outdatedStudent);
      return outdatedStudent;
    }
    return null;
  }

  public User updateCompany(int userId, Company updatedCompany) {
    Company outdatedCompany = companyRepository.findCompanyById(userId);
    if (outdatedCompany != null) {
      updateBasicUserCred(outdatedCompany, updatedCompany);
      outdatedCompany.setCompanyAddress(updatedCompany.getCompanyAddress());
      outdatedCompany.setCompanyName(updatedCompany.getCompanyName());
      userRepository.save(outdatedCompany);
      return outdatedCompany;
    }
    return null;
  }

  private void updateBasicUserCred(User outdatedUser, User updatedUser) {
    outdatedUser.setEmail(updatedUser.getEmail());
    outdatedUser.setFirstName(updatedUser.getFirstName());
    outdatedUser.setLastName(updatedUser.getLastName());
    outdatedUser.setPassword(updatedUser.getPassword());
    outdatedUser.setDob(updatedUser.getDob());
    outdatedUser.setPhoneNumbers(updatedUser.getPhoneNumbers());
  }

  public boolean deleteUser(int userId) {
    if (!userRepository.existsById((userId))) {
      return false;
    }
    userRepository.deleteById(userId);
    return true;
  }

  public Role getRole(int userId) {
    Optional<User> optionalUser = userRepository.findById(userId);
    return optionalUser.map(User::getRole).orElse(null);
  }
}
