package org.zerock.b01.repository.search;

import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.b01.domain.Board;
import org.zerock.b01.domain.QBoard;

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
        this.getQuerydsl().applyPagination(pageable, query);

        // select * from board where title like '%1%';
        List<Board> list = query.fetch();

        // select count(bno) from board where title like '%1%';
        long count = query.fetchCount();

        return null;
    }
}
