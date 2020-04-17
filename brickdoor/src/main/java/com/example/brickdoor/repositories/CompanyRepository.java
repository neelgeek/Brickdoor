package com.example.brickdoor.repositories;

import com.example.brickdoor.models.Company;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CompanyRepository extends CrudRepository<Company,Integer> {
  @Query("SELECT company FROM Company company WHERE company.id=:companyId")
  Company findCompanyById(@Param("companyId") int companyId);
}
