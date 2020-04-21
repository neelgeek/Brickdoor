package com.example.brickdoor.controllers;


import com.example.brickdoor.daos.UserDao;
import com.example.brickdoor.models.Student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AdminRestRoutes {
  @Autowired
  private UserDao userDao;

  @GetMapping("/admin/getAllStudents")
  public List<Student> getAllStudents(){
    List<Student> students = userDao.getAllStudents();
    return students;
  }
}
