package org.zerock.b01.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// 페이지를 보내는 것이 아니라 데이터만 보내주는 것
@RestController
@Log4j2
public class SampleJSONController {

    @GetMapping("/helloArr")
    public String[] helloArr() {
        log.info("helloArr...........");
        return new String[]{"AAA", "BBB", "CCC"};
    }
}
