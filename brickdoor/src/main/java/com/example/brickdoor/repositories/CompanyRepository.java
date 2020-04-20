package com.example.brickdoor.repositories;

import com.example.brickdoor.models.Company;
import com.example.brickdoor.models.Role;

import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends CrudRepository<Company,Integer> {
  @Query("SELECT company FROM User company WHERE company.id=:companyId")
  Company findCompanyById(@Param("companyId") int companyId);

  @Query("SELECT company FROM User company WHERE company.role=:role")
  List<Company> getAllCompanies(@Param("role") Role role);

  @Query("SELECT companies FROM User companies WHERE companies.role=:role AND companies.companyName LIKE :query%")
  Set<Company> searchCompanies(@Param("query") String query, @Param("role") Role role);

//  @Query("SELECT following FROM User following WHERE following.role=:role AND following.id IN "
//      + "(SELECT f.following_id FROM follows f "
//      + "JOIN User u on "
//      + "f.follower_id = u.id "
//      + "WHERE u.id=:userId)")
//  List<Company> getFollowingCompanies(@Param("userId") int userId, @Param("role") Role role);
}
