package org.zerock.b02.repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.b02.domain.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

//  쿼리문이 객체 형식으로 r은 댓글객체 r.board.bno(게시글 번호) :bno(입력변수)
  @Query("select r from Reply r where r.board.bno = :bno")
  Page<Reply> listOfBoard(Long bno, Pageable pageable);
}
