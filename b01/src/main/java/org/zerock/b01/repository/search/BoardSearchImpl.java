package org.zerock.b01.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.b01.domain.Board;
import org.zerock.b01.domain.QBoard;
import org.zerock.b01.domain.QReply;
import org.zerock.b01.dto.BoardListReplyCountDTO;

import java.util.List;

// 반드시 상속 받은 인터페이스명 + Impl로 명명
public class BoardSearchImpl extends QuerydslRepositorySupport implements BoardSearch {

    public BoardSearchImpl() {
        super(Board.class);
    }

    // 우리는 BoardRepository 내부에서 호출할 메서드를
    // QueryDsl로 구현함
    @Override
    public Page<Board> search1(Pageable pageable) {

        // 우리가 작업하는 QueryDsl 프로그램이 JPQL로 변환하기 위해 사용
        QBoard board = QBoard.board;    // Q 도메인 객체

        /*
         Querydsl을 통해서 한 단계씩 sql문을 작성해나간다.
         */

        // From board
        JPQLQuery<Board> query = from(board);   // select .. from board

        // where title like '%1%'
        query.where(board.title.contains("1")); // where title like ...

        // paging
        // order by bno desc limit 1, 10;
        this.getQuerydsl().applyPagination(pageable, query);

        // select * from board where title like '%1%' order by bno desc limit 1, 10;
        List<Board> list = query.fetch();

        // select count(bno) from board where title like '%1%';
        long count = query.fetchCount();

        return null;
    }

    @Override
    public Page<Board> searchAll(String[] types, String keyword, Pageable pageable) {

        // Querydsl을 JPQL 변환하기 위한 역할
        QBoard board = QBoard.board;
        JPQLQuery<Board> query = from(board);

        if((types != null && types.length > 0) && keyword != null) {    // 검색 조건과 키워드가 있다면

            // ( 의 역할을 해줌
            BooleanBuilder booleanBuilder = new BooleanBuilder();

            /*
            title like '%1%'
            or content like '%1%'
            or writer like '%1%'
             */

            for(String type : types) {

                switch (type) {
                    case "t":
                        booleanBuilder.or(board.title.contains(keyword));
                        break;
                    case "c":
                        booleanBuilder.or(board.content.contains(keyword));
                        break;
                    case "w":
                        booleanBuilder.or(board.writer.contains(keyword));
                        break;
                }
            }
            // )

            /*
            where (
                title like '%1%'
                or content like '%1%'
                or writer like '%1%'
            )
             */
            query.where(booleanBuilder);
        }

        // and bno > 0L
        query.where(board.bno.gt(0L));

        // paging
        // order by bno desc limit 1, 10;
        this.getQuerydsl().applyPagination(pageable, query);

        /*"
        select count(bno)
            from board
            where (
                title like '%1%'
                or content like '%1%'
                or writer like '%1%'
            )
            and bno > 0L
            order by bno desc limit 1, 10;
         */
        List<Board> list = query.fetch();

        long count = query.fetchCount();

        /*
        list: 실제 row 리스트들
        pageable: 페이지 처리하는 데 필요한 정보
        count: 전체 row 개수

        페이징 처리하려면 이것들이 모두 필요하므로 묶어서 넘기려고
        Page<E>를 상속받은 PageImpl<E> 클래스를 만들어 놓은 것이다.
         */
        return new PageImpl<>(list, pageable, count);
    }

    @Override
    public Page<BoardListReplyCountDTO> searchWithReplyCount(String[] types, String keyword, Pageable pageable) {

        QBoard board = QBoard.board;
        QReply reply = QReply.reply;

       /*
        왼쪽 테이블인 board에 기준을 맞춰서 board의 row에 댓글이 존재하지 않아도 row를 모두 출력해줘
`       FROM board LEFT OUTER JOIN reply ON reply
        */

        // FROM board
        JPQLQuery<Board> query = from(board);

        // LEFT OUTER JOIN reply ON reply
        query.leftJoin(reply).on(reply.board.eq(board));

        //GROUP BY board의 컬럼들
        query.groupBy(board);

        // OR가 AND보다 연산자 우선순위가 낮기 때문에 OR 조건들은 괄호로 묵어줌
        if((types != null && types.length > 0) && keyword != null) {

            BooleanBuilder booleanBuilder = new BooleanBuilder();   // (

            for(String type :types) {

                switch (type) {
                    case "t":
                        // OR board.title LIKE '%:keyword%'
                        booleanBuilder.or(board.title.contains(keyword));
                        break;
                    case "c":
                        // OR board.content LIKE '%:keyword%'
                        booleanBuilder.or(board.content.contains(keyword));
                        break;
                    case "w":
                        // OR board.writer LIKE '%:keyword%'
                        booleanBuilder.or(board.writer.contains(keyword));
                        break;
                }
            }

            /*
            WHERE (
                board.title LIKE '%:keyword%
                OR board.content LIKE '%:keyword%'
                OR board.writer LIKE '%:keyword%'
            )
             */
            query.where(booleanBuilder);    // )
        }

        // AND board.bno > 0
        query.where(board.bno.gt(0L));

        // SELECT bno, title, writer, regDate, COUNT(reply) replyCount
        JPQLQuery<BoardListReplyCountDTO> dtoQuery = query.select(Projections.
                bean(BoardListReplyCountDTO.class,
                        board.bno,
                        board.title,
                        board.writer,
                        board.regDate,
                        reply.count().as("replyCount")
                ));

        // paging 처리를 dtoQuery에 적용해줌(페이징 처리 sql문을 추가해줌)
        this.getQuerydsl().applyPagination(pageable, dtoQuery);

        // 쿼리 실행
        List<BoardListReplyCountDTO> dtoList = dtoQuery.fetch();

        // 현재 결과 전체 개수
        long count = dtoQuery.fetchCount();

        // tymeleaf html에 전달하기 위해서
        return new PageImpl<>(dtoList, pageable, count);
    }
}
