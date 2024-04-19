package org.zerock.b01.domain;

import lombok.*;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.*;

/*
DB 논리적 설계 단계에서 물리적 설계로 전환되기 전에
물리적 Table로 생성되어야 할 논리적 묶음을 Entity라고 함

그래서 종종 Entity와 Table을 동일한 개념으로 사용하곤 함
 */
// 이 클래스의 정보를 가지고 자동으로 Board Table 생성할 거야
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@RestController
public class Board extends BaseEntity{

    /*
    @Id는 Pk(Primary Key)로 정의한다는 의미
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    는 Mysql/MariaDB에서 auto_increment 속성 부여
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bno;

    @Column(length = 500, nullable = false) // 칼럼의 길이와 null 허용 여부
    private String title;

    @Column(length = 2000, nullable = false)
    private String content;

    @Column(length = 50, nullable = false)
    private String writer;

    public void change(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
