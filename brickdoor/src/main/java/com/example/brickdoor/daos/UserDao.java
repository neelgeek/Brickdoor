package com.example.brickdoor.daos;

import com.example.brickdoor.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserDao {

  @Autowired
  private UserRepository userRepository;
}
