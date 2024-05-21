package com.ssafy.finalproject.aptshool.repository;

import com.ssafy.finalproject.aptshool.entity.AptSchoolInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AptSchoolInfoRepository extends JpaRepository<AptSchoolInfo, Long> {
    Optional<AptSchoolInfo> findByAptCodeStartingWithAndAptNameStartingWith(String aptCode, String aptName);
}