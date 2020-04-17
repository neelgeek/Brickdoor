package com.example.brickdoor.daos;

import com.example.brickdoor.models.User;
import com.example.brickdoor.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserDao {

  @Autowired
  private UserRepository userRepository;

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

}
