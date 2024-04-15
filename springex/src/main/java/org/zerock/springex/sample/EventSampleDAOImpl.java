package org.zerock.springex.sample;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
// @Primary를 사용하지 않고 이렇게 event라는 이름을 줄 수도 있음
@Qualifier("event")
// 이걸 안 쓰면 SampleDAO 타입의 객체가 하나가 아니라 2개이므로 에러가 뜸. 지금 사용하고 싶은 것에 @Primary 어노테이션 부여
//@Primary
public class EventSampleDAOImpl implements SampleDAO{
}
