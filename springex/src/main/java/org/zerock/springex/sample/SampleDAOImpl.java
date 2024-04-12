package org.zerock.springex.sample;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

// SampleDAO를 인터페이스로 변경하면서 지웠던 @Repository를 여기에 작성해줌
@Repository
// normal이라는 이름으로 만들어짐
@Qualifier("normal")
public class SampleDAOImpl implements SampleDAO{

}
