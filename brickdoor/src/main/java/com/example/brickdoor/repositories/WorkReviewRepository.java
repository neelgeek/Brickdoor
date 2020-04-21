package com.example.brickdoor.repositories;

import com.example.brickdoor.models.Review;
import com.example.brickdoor.models.WorkReview;

import org.hibernate.jdbc.Work;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WorkReviewRepository extends CrudRepository<WorkReview, Integer> {

    @Query(value = "SELECT review FROM WorkReview review")
    public List<WorkReview> findAllWorkReview();


    @Query(value = "SELECT review FROM WorkReview review JOIN review.company company WHERE company.id=:cId")
    public List<WorkReview> findWorkReviewsByCompanyId(@Param("cId") int cId);

    @Query(value = "SELECT review FROM WorkReview review JOIN review.student student WHERE student.id=:sId")
    public List<WorkReview> findWorkReviewByStudentId(@Param("sId") int studentId);

    @Query(value = "SELECT review FROM WorkReview review where review.id=:wId")
    public WorkReview findWorkReviewById(@Param("wId") int wId);
}
