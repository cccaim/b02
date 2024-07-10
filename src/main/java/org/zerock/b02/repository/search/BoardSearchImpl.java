package org.zerock.b02.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.b02.domain.Board;
import org.zerock.b02.domain.QBoard;

import java.util.List;

public class BoardSearchImpl extends QuerydslRepositorySupport implements BoardSearch {

  public BoardSearchImpl(){
    super(Board.class);
  }

  @Override
  public Page<Board> search1(Pageable pageable) {

    QBoard board = QBoard.board;

    JPQLQuery<Board> query = from(board);

    BooleanBuilder booleanBuilder = new BooleanBuilder(); // (

    booleanBuilder.or(board.title.contains("11")); // title like ...

    booleanBuilder.or(board.content.contains("11")); // content like ....

    query.where(booleanBuilder);
    query.where(board.bno.gt(0L));


    //paging
    this.getQuerydsl().applyPagination(pageable, query);

    List<Board> list = query.fetch();

    long count = query.fetchCount();


    return null;

  }

  @Override
  public Page<Board> searchAll(String[] types, String keyword, Pageable pageable) {

    QBoard board = QBoard.board;
    JPQLQuery<Board> query = from(board);

    if( (types != null && types.length > 0) && keyword != null ){ //검색 조건과 키워드가 있다면

      BooleanBuilder booleanBuilder = new BooleanBuilder(); // (

      for(String type: types){

        switch (type){
          case "t":  //제목
            booleanBuilder.or(board.title.contains(keyword));
            break;
          case "c":  //내용
            booleanBuilder.or(board.content.contains(keyword));
            break;
          case "w":  //글쓴이
            booleanBuilder.or(board.writer.contains(keyword));
            break;
        }
      }//end for
      query.where(booleanBuilder);
    }//end if

    //And bno > 0 (글번호가 0보다 큰 조건)
    query.where(board.bno.gt(0L));

    //paging을 적용한 결과 (몇 페이지의 10개 데이터만 리턴됨)
    this.getQuerydsl().applyPagination(pageable, query);

    List<Board> list = query.fetch();

    long count = query.fetchCount();
    //페이지 객체로 리턴함.
    return new PageImpl<>(list, pageable, count);

  }

}