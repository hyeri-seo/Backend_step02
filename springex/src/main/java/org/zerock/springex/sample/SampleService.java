package org.zerock.springex.sample;

import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service    // 도메인 기능에 부여
@ToString
public class SampleService {

    // 필드 주입 방식
    @Autowired
    private SampleDAO sampleDAO;
}
