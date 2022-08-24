package com.prac.springprac.repository;

import com.prac.springprac.domain.Member;
import com.prac.springprac.repository.MemberRepository;
import com.prac.springprac.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class MemoryMemberRepositoryTest {

    MemberRepository memberRepository= new MemoryMemberRepository();
    MemoryMemberRepository memoryMemberRepository = new MemoryMemberRepository();

    @AfterEach
    public void afterEach(){
        memoryMemberRepository.clear();;
    }
    @Test
    public void save(){
        //save 테스트 위해서는 저장해보고 도출하는 값을 비교해보면 true/false 나오지 않을까 생각
        //given
        Member member = new Member();
        member.setName("seungho");

        //when
        memberRepository.save(member);

        //then
        /* optional 반환은 .get 처리를 통해 optional을 벗길 수 있다. 바로 get이 잘 오진 않으나, 테스트 케이스에선 자주 사용
        이제 member와 저장소에서 뽑아온 result로 비교시작 */
        Member result = memberRepository.findById(member.getId()).get();

        Assertions.assertThat(member.getId()).isEqualTo(result.getId());
        Assertions.assertThat(member.getName()).isEqualTo(result.getName());
        Assertions.assertThat(member).isEqualTo(result);
        System.out.println(member.getId());
        System.out.println(result.getId());
    }

    @Test
    public void findByName(){
        Member member1 = new Member();
        member1.setName("seungho");

        Member member2 = new Member();
        member2.setName("yujin");

        memberRepository.save(member1);
        memberRepository.save(member2);

        Member result1 = memberRepository.findByName(member1.getName()).get();
        Member result2 = memberRepository.findByName(member2.getName()).get();

        Assertions.assertThat(member1).isEqualTo(result1);
        Assertions.assertThat(member2).isEqualTo(result2);
        Assertions.assertThat(member1.getName()).isEqualTo(result1.getName());

    }
    @Test
    public void findAll(){
        Member member1 = new Member();
        Member member2 = new Member();
        Member member3 = new Member();

        member1.setName("seungho");
        member2.setName("yujin");
        member3.setName("dongdong");

        memberRepository.save((member1));
        memberRepository.save((member2));
        memberRepository.save((member3));

        List<Member> results = (List<Member>)memberRepository.findAll();

        Assertions.assertThat(results.size()).isEqualTo(3);
        Member testMember = results.stream().
                filter(member -> member.getName().
                        equals("yujin")).findAny().get();

        Assertions.assertThat(testMember.getName()).isEqualTo("yujin");


    }
}
