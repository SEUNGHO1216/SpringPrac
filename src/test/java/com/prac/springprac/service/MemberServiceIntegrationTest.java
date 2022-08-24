package com.prac.springprac.service;

import com.prac.springprac.domain.Member;
import com.prac.springprac.repository.MemberRepository;
import com.prac.springprac.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
@Transactional
/*transaction을 달아야 commit을 안 시키고 롤백을 시킨다. 하나의 테스트 단위마다 롤백시켜버린다. AfterEach로 지워줄 필요 x
테스트 케이스에 이 애노테이션이 있으면, 테스트 시작 전에 트랜잭션을 시작하고, 테스트 완료 후에 항상 롤백한다.
이렇게 하면 DB에 데이터가 남지 않으므로 다음 테스트에 영향을 주지 않는다.*/
public class MemberServiceIntegrationTest {
    /*테스트 코드는 빌드 시 실제 코드에 포함되지 않는다*/

   @Autowired
    MemberService memberService;
   @Autowired
    MemberRepository memberRepository;

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
