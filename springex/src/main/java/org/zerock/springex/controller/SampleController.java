package org.zerock.springex.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.springex.dto.TodoDTO;

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
    public void ex3(LocalDate dueDate) {
        log.info("ex3.............");
        log.info("dueDate: " + dueDate);
    }

    @GetMapping("/ex4")
    public void ex4(Model model) {
        log.info("------------------------");
        
        // model에 추가한 값을 꺼내서 request에 저장함
        model.addAttribute("message", "Hello World");
    }

    /*
    웹에서 수신된 parameter들은 todoDTO의 동일한 필드에 저장되고,
    todoDTO라는 이름으로 request에 전달됨
    name이라는 이름으로 Albert가 request에 전달됨
    
    request에 전달된다는 의미는 결국 jsp에서 꺼내어 쓸 수 있다는 의미
     */
    @GetMapping("ex4_1")
    public void ex4Extra(TodoDTO todoDTO, Model model) {
        log.info(todoDTO);
        model.addAttribute("name", "Albert");
    }

    /*
    todoDTO를 dto이라는 이름으로 변경해서 request에 저장함
     */
    @GetMapping("ex4_2")
    public void ex4Extra2(@ModelAttribute("dto") TodoDTO todoDTO, Model model) {
        log.info(todoDTO);
        model.addAttribute("name", "Albert");
    }

    @GetMapping("/ex5")
    public String ex5(RedirectAttributes redirectAttributes) {

        // 리다이렉션 시 필요한 정보를 전달하는 것
        // 주소 옆에 매개변수로 주소와 함께 parameter로 전달됨
        redirectAttributes.addAttribute("name", "ABC");
        
        // 서버가 재접속명령을 브라우저한테 보냈을 때 한 번만 사용할 수 있는 일회성 데이터
        redirectAttributes.addFlashAttribute("result", "success");

        // 리턴값이 존재하면 jsp로 전달되는 것이 아니라,
        // 브라우저한테 아래 주소로 재접속을 하도록 하는 것
        return "redirect:/ex6";
    }

    @GetMapping("/ex6")
    public void ex6() {
        // WEB-INF/views/ex6.jsp로 이동함
    }

    @GetMapping("/ex7")
    public void ex7(String p1, int p2) {
        log.info("p1....." + p1);
        log.info("p2....." + p2);
    }
}
