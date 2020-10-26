package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @GetMapping("hello") //get방식
    public String hello(Model model) {
        model.addAttribute("data","spring!!");
        return "hello";

        /*
        * 컨트롤러에서 리턴값으로 문자를 반환하면 view resolver가 화면을 찾아서 처리한다.
        *
        * 스프링 부트 템플릿엔진 기본 viewName매핑 (templates폴더 아래 파일을 찾는다.)
        * `resources:templates/` + {viewName} + `.html`
        *
        * cf : `spring-boot-devtools`라이브러리를 추가하면, `.html`파일을 컴파일만
        * 해주면 서버 재시작 없이 view파일 변경이 가능하다.
       * */

    }

    @GetMapping("hello-template")
    public String helloMvc(@RequestParam("name") String name, Model model) {
        model.addAttribute("name",name);
        return "hello-template";
    }


    @GetMapping("hello-string")
    @ResponseBody //http의 body부에 리턴하는 데이터를 직접 넣어주겠다는 의미
    public String helloString(@RequestParam("name") String name) {
        return "hello " + name;
    }

    @GetMapping("hello-api")
    @ResponseBody
    public Hello HelloApi(@RequestParam("name") String name) {
        Hello hello = new Hello();
        hello.setName(name);
        return hello;

        /*
        *@ResponseBody return:hello(name:spring)
        * >> HTTP의 BDOY에 문자 내용을 직접 반환
        * >> viewResolver대신에 HttpMessageConverter가 동작
        *
        * ** HttpMessageConverter
        * 1. JsonConverter (MappingJackson2HttpMessageConverter : 기본 객체처리)
        * 2. StringConverter : 기본 문자처리
        *
        * >> byte처리 등등 기타 여러 HttpMessageConverter가 기본으로 등록되어있음.
        *
        * 클라이언트의 HTTP Accept헤더와 서벗의 컨트롤러 반환 타입 정보 두가지를 조합해서
        * 'HttpMessageConverter'가 선택된다.
        * */
    }


    static class Hello {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }


/*
* ****일반적인 웹 애플리케이션 계층 구조
*
*   Controller    --->    Service   --->  Repository ---> DB
*        |                    |                 |
*        |____________________|_________________|
*                             |
*                          Domain
*
* 컨트롤러 : 웹 MVC 의 컨트롤러 역할
* 서비스 : 핵심 비즈니스 로직 구현
* 리포지토리 : 데이터베이스 접근, 도메인 객체를 DB에 저장
* 도메인 : 비즈니스 도메인 객체 ex)회원, 주문, 쿠폰 등등 주로 DB에 저장하고 관리됨
*
*
*  ****클래스 의존관계
*
*    MemberService --------> MemberRepository
*                             (Interface)
*                                  ^
*                                  |
*                                  |
*                           Memory MemberRepository
*
*
* 아직 DB가 선정되지 않아서 우선 인터페이스로 구현클래스를 변경할 수 있도록 설계
* 개발을 진행하기 위해서 초기 개발 단게에서는 구현체로 가벼운 메모리 기반의 데이터 저장소 사용
*
* * */


}
