package com.ssafy.finalproject.like.repository;

import com.ssafy.finalproject.like.entity.LikeApt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikeAptRepository extends JpaRepository<LikeApt, Long> {

    List<LikeApt> findByMemberId(Long memberId);

}
