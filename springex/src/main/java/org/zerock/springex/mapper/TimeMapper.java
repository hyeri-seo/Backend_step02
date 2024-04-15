package org.zerock.springex.mapper;

import org.apache.ibatis.annotations.Select;

public interface TimeMapper {

    /* getTime 메소드가 실행될 때 @Select 안에 있는 쿼리문이 실행되고
       결과값인 시간을 문자열로 반환함 */
    @Select("select now()")
    String getTime();
}
