package com.example.brickdoor.repositories;

import com.example.brickdoor.models.Student;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface StudentRepository extends CrudRepository<Student,Integer> {
  @Query("SELECT student FROM User student WHERE student.id=:studentId")
  Student findStudentById(@Param("studentId") int studentId);
}
