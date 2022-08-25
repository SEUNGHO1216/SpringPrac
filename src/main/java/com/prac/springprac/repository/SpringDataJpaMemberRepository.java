package com.prac.springprac.repository;

import com.prac.springprac.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {
//인터페이스는 다중 상속가능

    @Override
    Optional<Member> findByName(String name);
}
