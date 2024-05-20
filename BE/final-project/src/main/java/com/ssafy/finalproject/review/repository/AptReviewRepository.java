package com.ssafy.finalproject.review.repository;

import com.ssafy.finalproject.review.entity.AptReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AptReviewRepository extends JpaRepository<AptReview,Long> {

    List<AptReview> findByApartmentId(String apartmentId);

}
