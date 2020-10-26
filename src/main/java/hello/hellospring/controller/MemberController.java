package hello.hellospring.controller;

import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;
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
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/members/new")
    public String createForm(){
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(MemberForm form) {
        Member member = new Member();
        member.setName(form.getName());

        memberService.join(member);

        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }
}

/*
* 스프링 빈을 등록하는 2가지 방법
* 
* 1. 컴포넌트 스캔과 자동 의존관계 설정
*   @Component 어노테이션이 있으면 자동으로 빈으로 등록된다.
*   @Controller 컨트롤러가 스프링 빈으로 자동 등록된 이유도 컴포넌트 스캔 때문이다.
*   @Component를 포함하는 어노테이션들 : @Controller, @Service, @Repository
*
* 2. 자바 코드로 직접 스프링 빈 등록
*    service > SpringConfig.java 파일 참조
*
* 스프링은 스프링 컨테이너에 스프링 빈을 등록할때 기본으로 싱글톤으로 등록한다.
* (유일하게 하나만 등록해서 공유). 따라서 같은 스프링 빈으면 모두 같은 인스턴스이다.
* 설정으로 싱글톤이 아니게 설정할 수 있지만, 특수한 경우 아니면 대부분 싱글톤을 사용한다.
*
* */
