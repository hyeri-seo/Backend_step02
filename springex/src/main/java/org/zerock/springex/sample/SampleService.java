package org.zerock.springex.sample;

import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service    // 도메인 기능에 부여
@ToString
@RequiredArgsConstructor
public class SampleService {

    // lombok의 @RequiredArgsConstructor을 사용해서 '생성자 주입 방식'으로 객체를 주입함
    // 반드시 final로 선언해야 함
    private final SampleDAO sampleDAO;

    // 필드 주입 방식 - 예전에 쓰던 방식
    /* @Autowired
    private SampleDAO sampleDAO;
    */
}
