package com.prac.springprac.controller;

import com.prac.springprac.domain.Member;
import com.prac.springprac.repository.MemberRepository;
import com.prac.springprac.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService){
        this.memberService=memberService;
    }

    @GetMapping("/members/new")
    public String registerForm(){
        return "members/registerForm";
    }

    @PostMapping("/members/new")
    public String registerSubmit(MemberRegister memberRegister){
        Member member = new Member();
        member.setName(memberRegister.getName());
        memberService.join(member);

        return "redirect:/";
    }

    @GetMapping("/members")
    public String showMembers(Model model){
        List<Member> members = memberService.findMembers();
        model.addAttribute("members",members);

        return "members/showMembers";
    }
}
