package org.zerock.springex.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

/*
jsp에서 bootstrap의 pagination 컴포넌트를 사용하는데
그때 이 dto를 받아서 pagination을 구성한다.
 */
/*
현재는 E에 TodoDto가 전달됨
하지만 다른 업무/목적으로 얼마든지 많은 테이블과 dto가 생성될 수 있음
이때 dtoList를 제외한 나머지 값들은 어떤 page에서도 pagination에 꼭 필요한 데이터임
dtoList만 할 일 페이지 업무일 때는 TodoDTO
    예를 들어 Project 업무일 때는 ProjectDTO
    쇼핑 구매 업무일 때는 OrderDTO 등의 리스트가 필요하므로
    Generic으로 만들면 한 번 만들어두고 재사용이 가능함
 */
@Getter
@ToString
public class PageResponseDTO<E> {

    private int page;
    private int size;
    private int total;

    // jsp화면에 pagination 시작 페이지 번호
    private int start; //
    // jsp화면에 pagination 끝 페이지 번호
    private int end;

    // 이전 페이지가 존재하면 true, prev 버튼이 보임
    // false면 감춤
    private boolean prev;
    // 다음 페이지가 존재하면 true, next 버튼이 보임
    // false면 감춤
    private boolean next;

    // 테이블에 보여줘야 할 row 데이터의 list
    private List<E> dtoList;

    // 생성자를 호출할 때 withAll이라는 이름으로 호출하겠다
    @Builder(builderMethodName = "withAll")
    public PageResponseDTO(PageRequestDTO pageRequestDTO, List<E> dtoList, int total) {

        this.page = pageRequestDTO.getPage();
        this.size = pageRequestDTO.getSize();

        this.total = total;
        this.dtoList = dtoList;

        // 내 page 번호에 따라 paination의 끝 번호를 구하고 -9를 해주면 시작 번호를 구할 수 있다.
        // 10.은 pagination의 범위이다.
        // 예) 현재 페이지가 1~10페이지면 페이지 블록의 끝페이지가 10페이지
        this.end = (int)(Math.ceil(this.page / 10.0)) * 10;

        // 9는 pagination 범위 - 1
        // 현재 페이지 블록의 끝 페이지가 10이라면 현재 페이지 블록의 시작 페이지는 1
        this.start = this.end - 9;

        // pagination의 가장 끝 번호
        // size는 한 페이지에 보여줄 정보량
        // 예) 댓글이 총 782개이고, 한 페이지에 보여줄 댓글의 양이 10개라면
        // 최종 페이지는 79페이지여야 한다.
        int last = (int)(Math.ceil(total/(double)size));

        /*
        위에서 end 계산은 10 단위로 무조건 맞춘 것이기 때문에
        실제 마지막 페이지 번호와 확인해서
        마지막 페이지인 경우가 end > last 이므로
        end = last로 해줘야
        마지막 페이지인 경우 pagination이 알맞게 나오게 됨
         */
        // 예) 댓글이 총 782개이고 현재 78페이지라면 end는 80이 된다.
        // 그런데 최종 페이지는 79페이지이기 때문에,
        // end가 last보다 크면 end 값에 최종 페이지인 79를 넣어주는 것이다.
        this.end = end > last ? last : end;

        /*
        1~10 pagination이 아니고 그 이상이라는 의미이므로 이전 페이지가 존재
         */
        // 현재 12페이지라면 1~10 페이지가 있는 이전 페이지 블록이 존재한다.
        this.prev = this.start > 1;

        /*
        현재 페이지네이션이 보여주는 범위가 마지막이 아니므로 다음 페이지가 존재
         */
        this.next = total > this.end * this.size;
    }
}
