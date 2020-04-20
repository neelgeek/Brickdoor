package com.example.brickdoor.repositories;

import com.example.brickdoor.models.Role;
import com.example.brickdoor.models.Student;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends CrudRepository<Student,Integer> {
  @Query("SELECT student FROM User student WHERE student.id=:studentId AND student.role=:role")
  Student findStudentById(@Param("studentId") int studentId, @Param("role") Role role);

  @Query("SELECT student FROM User student WHERE student.role=:role")
  List<Student> getAllStudents(@Param("role") Role role);

  @Query(
      "SELECT students FROM User students WHERE students.role=:role AND "
          + "(students.username LIKE :query% "
          + "OR students.firstName LIKE :query% "
          + "OR students.lastName LIKE :query%)"
  )
  Set<Student> searchStudents(@Param("query") String query, @Param("role") Role role);
//
//  @Query("SELECT following FROM User following WHERE following.role=:role AND following.id IN "
//      + "(SELECT f.following_id FROM follows f "
//      + "JOIN User u on "
//      + "f.follower_id = u.id "
//      + "WHERE u.id=:userId)")
//  List<Student> getFollowingStudents(@Param("userId") int userId, @Param("role") Role role);
}
