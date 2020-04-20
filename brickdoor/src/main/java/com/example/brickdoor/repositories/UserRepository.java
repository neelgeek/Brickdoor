package com.example.brickdoor.repositories;

import com.example.brickdoor.models.User;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User,Integer> {

  @Query("SELECT user FROM User user WHERE user.username=:username AND user.password=:password")
  User findUserByUserCredentials(@Param("username") String username, @Param("password") String password);

  @Query("SELECT user FROM User user WHERE user.username=:username")
  User findUserByUsername(@Param("username") String username);

  @Query("SELECT user FROM User user WHERE user.email=:email")
  User findUserByEmail(@Param("email") String email);
//
//  @Query("SELECT followers FROM User followers WHERE followers.id IN "
//      + "(SELECT f.follower_id FROM follows f "
//      + "JOIN User u on "
//      + "f.following_id = u.id "
//      + "WHERE u.id=:userId )")
//  List<User> getFollowers(@Param("userId") int userId);

//  @Query("SELECT following FROM User following WHERE following.id IN "
//      + "(SELECT f.following_id FROM follows f "
//      + "JOIN User u on "
//      + "f.follower_id = u.id "
//      + "WHERE u.id=:userId)")
//  List<User> getFollowing(@Param("userId") int userId);
}
