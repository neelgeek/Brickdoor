package com.example.brickdoor.daos;

import com.example.brickdoor.repositories.ReviewRepository;
import com.example.brickdoor.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BrickDoorDao {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    public void truncateDatabase() {
        reviewRepository.deleteAll();
        userRepository.deleteAll();
    }
}
