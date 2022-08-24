package com.prac.springprac.service;

import com.prac.springprac.domain.Member;
import com.prac.springprac.repository.MemberRepository;
import com.prac.springprac.repository.MemoryMemberRepository;
import com.prac.springprac.service.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;


public class MemberServiceTest {
    /*테스트 코드는 빌드 시 실제 코드에 포함되지 않는다*/

    /*
    문제:MemberService 클래스에서 new 로 생성하고 있는 MemoryMemberRepository와 테스트케이스에서 new Memory~ 클래스 각 다른 인스턴스
        머 일단 지금은 Repository에 자료구조 map이 static 선언 돼있으니 괜찮지만 그렇지 않다면 서로 다른 인스턴스 사용으로 인해 정보가 섞일 우려가 있음
    해결:의존성을 주입시켜줌
    */
    MemberService memberService;
    MemberRepository memberRepository;

    @BeforeEach
    public void beforeEach(){
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach
    public void afterEach(){
        memberRepository.clear();
    }

    @Test
    public void join(){
        //given
        Member member1 = new Member();
        member1.setName("seungho");

        Member member2 = new Member();
        member2.setName("seungho2");

        //when
        memberService.join(member1);
        memberService.join(member2);

        //then
        Member result = memberService.findOne(member2.getId()).get();

        Assertions.assertThat(member2.getId()).isEqualTo(result.getId());
        Assertions.assertThat(member1.getName()).isNotEqualTo(result.getName());
    }

    @Test
    public void duplicateValidation(){
        //given
        Member member1 = new Member();
        member1.setName("seungho");

        Member member2 = new Member();
        member2.setName("seungho");
        //when
        memberService.join(member1);

        //then
        //try catch로 할수도 있지만 test라이브러리 사용이 더 편할듯
        //assertThrows를 이용하면 되고 (예외클래스, 어떤 경우에 예외클래스가 일어날지를 나타내는 람다식)으로 파라미터 구성
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> memberService.join(member2));
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다");
        /*
        try{
            memberService.join(member1);
            memberService.join(member2);
            fail("중복검사 실패"); //중복검사를 제대로 해주지 못하면 fail에 걸리게 돔
        }catch (IllegalArgumentException e){
            Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다");
        }
        */
    }

}
