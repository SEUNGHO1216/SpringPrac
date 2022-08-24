package com.prac.springprac.repository;

import com.prac.springprac.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


//@Repository
public class MemoryMemberRepository implements MemberRepository{

    private static Map<Long, Member> store = new ConcurrentHashMap<>();/*new HashMap<>();*/
    private static Long sequence = 0L;

    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        return store.put(member.getId(),member);
    }

    @Override
    public Optional<Member> findById(Long id) {
        //람다 사용 -> 밸류값인 멤버를 돌려서 아이디값이 일치하는 것을 findany로 하나만 찾는다!
        return Optional.ofNullable(store.get(id));
        /*return store.values().stream().
                filter(member -> member.getId().equals(id)).
                findAny();*/
    }

    @Override
    public Optional<Member> findByName(String name) {
        //람다 사용 -> 밸류값인 멤버를 돌려서 name값이 일치하는 것을 findany로 하나만 찾는다!
        return store.values().stream().
                filter(member -> member.getName().equals(name)).
                findAny();
    }

    @Override
    public List<Member> findAll() {
        //map 객체에 values()를 붙이면 value값만 쭉 Collector list형태로 나옴
        return new ArrayList<>(store.values());
    }

    public void clear(){
        //map 함수 중 하나인 clear를 사용하여 모든 요소를 다 지움
        store.clear();;
    }
}
