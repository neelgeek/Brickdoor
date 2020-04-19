package com.example.brickdoor.repositories;

import com.example.brickdoor.models.Admin;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends CrudRepository<Admin,Integer> {

  @Query("SELECT admin FROM User admin WHERE admin.id=:adminId")
  Admin findAdminById(@Param("adminId") int adminId);

}


