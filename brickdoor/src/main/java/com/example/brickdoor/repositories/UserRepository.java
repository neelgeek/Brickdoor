package com.example.brickdoor.repositories;

import com.example.brickdoor.models.User;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Integer> {

}
