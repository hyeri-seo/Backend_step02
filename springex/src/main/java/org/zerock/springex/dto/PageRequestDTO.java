package org.zerock.springex.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.Arrays;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageRequestDTO {

    @Builder.Default
    @Min(value = 1)
    @Positive
    private int page = 1;       // 페이지 번호

    @Builder.Default
    @Min(value = 10)
    @Max(value = 100)
    @Positive
    private int size =  10;     // 페이지당 보여줄 정보의 크기

    private String link;

    // 해당 페이지 이전에 몇 개를 건너 뛰어야 하는지 계산하는 메서드
    public int getSkip() {
        return (page-1) * 10;
    }

    /*
    /todo/list에서 /todo/read, /todo/modify 등으로 디오했다가
    다시 /todo/list로 돌아올 때 현재 내 페이지로 돌아오도록 하기 위해
    이 링크 정보를 붙여서 url 이동을 하는 용도로 사용함
     */
    public String getLink() {

        // 페이징 처리를 하기 위한 조건
//        if(link == null) {
            
        // StringBuilder는 내부에 character 배열이 있어서 append 이용해서 한 번에 추가해서 붙여서 나옴
        // String 이용해서 += 해줘도 되긴 함
        // 지나치게 문자열이 바뀌는 상황에서는 StringBuilder는 할당된 공간을 재사용하는 것이기 때문에 속도가 더 빠름
        // String은 새로운 문자열을 할당했다가 가비지 컬렉터에서 수거하기 때문에 속도가 느려질 수 있음
        StringBuilder builder = new StringBuilder();
        builder.append("page=" + this.page);
        builder.append("&size=" + this.size);
//            link = builder.toString();
        if(finished) {
            builder.append("&finished=on");
        }

        if(types != null && types.length > 0) {
            for(int i=0; i<types.length; i++) {
                builder.append("&types=" + types[i]);
            }
        }

        if(keyword != null) {
            try {
                builder.append("&keyword=" + URLEncoder.encode(keyword, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        if(from != null) {
            builder.append("&from" + from.toString());
        }

        if(to != null) {
            builder.append("&to=" + to.toString());
        }
//        }
        return builder.toString();
    }

    // -------------- 검색 조건 관련 필드 ------------------------

    private String[] types;     // 제목(t), 작성자(w)
    private String keyword;     // 검색어
    private boolean finished;   // 완료 여부
    private LocalDate from;     // 시작 시간
    private LocalDate to;       // 종료 시간

     public boolean checkType(String type) {

         if(types == null || types.length == 0) {
             return false;
         }
         return Arrays.stream(types).anyMatch(type::equals);
     }
}
