package org.zerock.springex.dto;

import lombok.*;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@ToString
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TodoDTO {

    private Long tno;

    // 브라우저에서 빈 걸 보내면 오류가 나도록
    @NotEmpty
    private String title;

    // 현재 시간보다 미래 시간이어야 함. 안 그러면 오류가 나도록
    @Future
    private LocalDate dueDate;

    private boolean finished;

    @NotEmpty
    private String writer;
}
