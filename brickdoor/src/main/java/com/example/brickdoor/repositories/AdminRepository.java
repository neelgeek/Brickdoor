package com.example.brickdoor.repositories;

import com.example.brickdoor.models.Admin;
import com.example.brickdoor.models.Role;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends CrudRepository<Admin,Integer> {

  @Query("SELECT admin FROM User admin WHERE admin.id=:adminId")
  Admin findAdminById(@Param("adminId") int adminId);

  @Query("SELECT admin FROM User admin WHERE admin.role=:role")
  List<Admin> getAllAdmin(@Param("role") Role role);

  @Query(
      "SELECT admins FROM User admins WHERE admins.role=:role AND "
          + "(admins.username LIKE :query% "
          + "OR admins.firstName LIKE :query% "
          + "OR admins.lastName LIKE :query%)"
  )
  Set<Admin> searchAdmin(@Param("query") String query, @Param("role") Role role);

}


