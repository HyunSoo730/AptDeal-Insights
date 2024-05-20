package com.ssafy.finalproject.member.repository;

import com.ssafy.finalproject.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {

    Member findByEmail(String email);

    Optional<Member> findById(Long id);
}
