package com.example.brickdoor.daos;

import com.example.brickdoor.models.User;
import com.example.brickdoor.repositories.UserRepository;

import java.util.Optional;
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

  public User updateUser(int userId, User updatedUser) {
    Optional<User> optionalUser = userRepository.findById(userId);

    if (optionalUser.isPresent()) {
      User outdatedUser = optionalUser.get();
      outdatedUser.setEmail(updatedUser.getEmail());
      outdatedUser.setFirstName(updatedUser.getFirstName());
      outdatedUser.setLastName(updatedUser.getLastName());
      outdatedUser.setPassword(updatedUser.getPassword());
      userRepository.save(outdatedUser);
      return outdatedUser;
    }
    return null;
  }
}
