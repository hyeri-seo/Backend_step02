package org.zerock.jdbcex.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

/**
 * VO는 처음 객체가 생성될 때 생성자를 통해서 한 번 초기화하면
 * 더 이상 값을 변경할 수 없도록 만드려는 의도
 * 
 * 왜냐하면 이 VO는 DB의 Table에서 값을 꺼내거나 저장 시에 사용하기 때문에
 * 혹시라도 모를 Table 데이터의 변경을 막기 위한 표현임
 *
 * 뒤에서 VO를 다시 Dto로 변환시켜서 Service - Controller - View 계층으로 전달됨
 */

@Getter         // getter 메소드를 만들어주는 것
@Builder        // 빌더 패턴, 생성자 초기화이지만 마치 필드 초기화
@ToString       // 필드 정보 보기
public class TodoVO {
    private Long tno;
    private String title;
    private LocalDate dueDate;
    private boolean finished;
}
