package com.prac.springprac.service;


import com.prac.springprac.domain.Member;
import com.prac.springprac.repository.MemberRepository;
import com.prac.springprac.repository.MemoryMemberRepository;

import java.util.List;
import java.util.Optional;

public class MemberService {

    /*service클래스는 memberRepository와 다르게 네이밍부터 좀더 비지니스 로직에 가까운 네이밍을 쓴다
    * 그만큼 비즈니스 로직을 수행하는 클래스라는 것을 입증하는 것이다.
    * */

    //DI(내부에서 매번 new 로 생성하는게 아니라 외부에서 주입 받아서 사용함)
    private final MemberRepository memberRepository;

    //외부에서 memberepository를 넣어줌
    public MemberService(MemberRepository memberRepository){
        this.memberRepository= memberRepository;
    }

    //회원가입
    public Long join(Member member){

        validateDuplicateMember(member); //중복검사
        memberRepository.save(member);
        return member.getId();

    }

    //중복검사 메소드
    private void validateDuplicateMember(Member member) {
        Optional<Member> result = memberRepository.findByName(member.getName());
        result.ifPresent(r->
                {throw new IllegalArgumentException("이미 존재하는 회원입니다");}
        );
    }
    //전체 회원 조회
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }
    //회원 한명 조회
    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById(memberId);
    }
}
