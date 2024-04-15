package org.zerock.springex.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

// (Page)Controller 역할을 하는 bean으로 생성해줘
@Controller
@Log4j2
public class SampleController {

    // /hello에 get 요청이 들어오면 이 메서드가 처리함
    @GetMapping("/hello")
    public void hello() {
        log.info("hello.............");

        // 리턴이 없으면 자동으로 /WEB-INF/views/hello.jsp로 이동하게 된다.
    }

    /* 요청이 전달될 때 name 변수와 age 변수의 값이 전달되므로 저장하라 */
    @GetMapping("/ex1")
    public void ex1(String name, int age) {
        log.info("ex1.............");
        log.info("name: " + name);
        log.info("age: " + age);
    }

    /*
    @RequestParam
    1) 웹에서 전달되는 parameter와 수신하는 메서드 파라미터의 이름이 일치하지 않아도 받을 수 있음
    2) 만약 전달되는 값이 없을 때 기본값을 지정할 수 있음
     */
    @GetMapping("/ex2")
    public void ex2(@RequestParam(name="name", defaultValue = "AAA") String name,
                    @RequestParam(name="age", defaultValue = "33") int age) {
        log.info("ex1.............");
        log.info("name: " + name);
        log.info("age: " + age);
        
        /* 
        이 처리가 끝나고 /WEB-INF/views/ex2.jsp로 이동할 때
        String name, int age는 request의 공간에 자동으로 저장 공유됨
        * */
    }

    /*
    날짜를 사용할 때 많이 사용하는 클래스
    java.util.Date
    java.sql.Date
    java.time.LocalDate
    
    웹에서 날짜 정보는 String으로 전달됨
    이때 포맷 변환이 필요함

    그래서 메서드 파라미터를 String형으로 한 다음에
    java 변환 코드로 처리하는 방법이 있지만,
    스프링은 이것을 보다 편하게 하도록 도와줌
     */
    @GetMapping("/ex3")
    public void e3(LocalDate dueDate) {
        log.info("ex3.............");
        log.info("dueDate: " + dueDate);
    }
}
