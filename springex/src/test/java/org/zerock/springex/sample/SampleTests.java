package org.zerock.springex.sample;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * ServletContext : tomcat의 전역영역, tomcat의 Container가 관리하는 영역
 * ApplicationContext : Spring Container가 관리하는 영역, 이곳에 bean(관리되는 객체)를 저장함
 */

@Log4j2
@ExtendWith(SpringExtension.class)  // junit4 @RunWith
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/root-context.xml")
public class SampleTests {

    // ApplicationContex에서 동일한 type을 찾아서 자동으로 주입해라
    // 필드 주입 방식
    @Autowired
    private SampleService sampleService;

    @Test
    public void testService1() {
        log.info(sampleService);
        Assertions.assertNotNull(sampleService);
    }
}
