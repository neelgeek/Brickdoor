package com.example.brickdoor.repositories;

import com.example.brickdoor.models.Role;
import com.example.brickdoor.models.User;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

  @Query("SELECT user FROM User user WHERE user.username=:username AND user.password=:password AND user.role=:role")
  User findUserByUserCredentials(@Param("username") String username, @Param("password") String password, @Param("role") Role role);

  @Query("SELECT user FROM User user WHERE user.username=:username")
  User findUserByUsername(@Param("username") String username);

  @Query("SELECT user FROM User user WHERE user.email=:email")
  User findUserByEmail(@Param("email") String email);

  @Modifying(clearAutomatically = true)
  @Query("UPDATE User SET role=:role where id=:userId")
  void updateRole(@Param("userId") int userId, @Param("role") Role role);
}
