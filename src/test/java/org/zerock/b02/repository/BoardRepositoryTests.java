package org.zerock.b02.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.b02.domain.Board;
import org.zerock.b02.domain.BoardImage;
import org.zerock.b02.dto.BoardListAllDTO;
import org.zerock.b02.dto.BoardListReplyCountDTO;
import org.zerock.b02.repository.BoardRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
@Log4j2
public class BoardRepositoryTests {

    @Autowired
    private BoardRepository boardRepository;
  @Autowired
  private ReplyRepository replyRepository;



    //@Test
    public void testInsert() {
        for (int i = 1; i <= 100; i++) {
            Board board = Board.builder()
                    .title("title..." + i)
                    .content("content..." + i)
                    .writer("user"+(i%10))
                    .build();
            Board result = boardRepository.save(board);
            log.info("BNO: " + result.getBno());
        }
    }

    //@Test
    public void testSelect(){
        Long bno = 1L;
        //Optional 타입은 없을 경우 null 값 에러 방지를 위함
        Optional<Board> result = boardRepository.findById(bno);
        Board board = result.orElseThrow(); //결과가 있을 경우에 가져오고 없으면 예외발생

        log.info(board.toString());
    }

    //update 기능 테스트
    //@Test
    public void testUpdate(){
        Long bno = 100L;
        //Optional 타입은 없을 경우 null 값 에러 방지를 위함
        Optional<Board> result = boardRepository.findById(bno);
        Board board = result.orElseThrow();
        board.change("update..title 100", "update..content 100");
        boardRepository.save(board);//update 도 save 메서드로 사용함
        // 1. 새로운 객체 입력시 새로 등록됨 (Create)
        // 2. 같은 객체 입력시 수정된 내용이 업데이트 됨 (기준은 id 기준)
    }

    //@Test
    public void testDelete(){
        long bno = 1L;
        boardRepository.deleteById(bno);
    }

    //게시판 페이징 테스트
    //@Test
    public void testPaging(){

        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());
        //페이지 객체를 findAll()에 입력하여 원하는 페이지의 데이터만 가져옴
        Page<Board> result = boardRepository.findAll(pageable);
        //페이지 리스트는 보드리스트와 여러 정보들이 담겨있음
        log.info("총 갯수: " +result.getTotalElements());
        log.info("총 페이지수: " + result.getTotalPages());
        log.info("페이지 번호: " + result.getNumber());
        log.info("페이지 사이즈: " + result.getSize());
        // 보드 리스트 데이터
        List<Board> boards = result.getContent();
        for (Board board : boards) {
            log.info(board.toString());
        }
    }

    @Test
    public void testSearch1(){

        String[] types = {"t","c","w"};

        String keyword = "1";

        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());
        //검색어로 검색조건(제목,내용,글쓴이)에 맞게 페이지 검색
        Page<Board> result = boardRepository.searchAll(types, keyword, pageable);

        //결과 확인
        log.info(result.getTotalPages());//전체 페이지
        log.info(result.getNumber());  //페이지번호
        log.info(result.hasPrevious() + ": " + result.hasNext());
        result.getContent().forEach(board -> log.info(board.toString()));
    }

    @Test
    public void testSearchReplyCount(){

        String[] types = {"t","c","w"};

        String keyword = "1";

        Pageable pageable = PageRequest.of(2, 10, Sort.by("bno").descending());
        //검색어로 검색조건(제목,내용,글쓴이)에 맞게 페이지 검색
        Page<BoardListReplyCountDTO> result = boardRepository.searchWithReplyCount(types, keyword, pageable);

        //결과 확인
        log.info(result.getTotalPages());//전체 페이지
        log.info(result.getSize());     //사이즈
        log.info(result.getNumber());  //페이지번호
        log.info(result.hasPrevious() + ": " + result.hasNext());
        result.getContent().forEach(board -> log.info(board.toString()));
    }

    @Test
    public void testInsertWithImges(){
        Board board = Board.builder()
                .title("이미지 테스트")
                .content("첨부파일 테스트")
                .writer("테스터")
                .build();
        for (int i = 0; i < 3; i++){
            board.addImage(UUID.randomUUID().toString(),"file"+i+".jpg");
        }
        boardRepository.save(board);
    }

    @Test
    public void testReadWithImages(){
        Optional<Board> result = boardRepository.findByIdWithImage(1L);
        Board board = result.orElseThrow();
//        반드시 존재하는 bno 로 확인
        log.info(board);
        log.info("==================");
        for(BoardImage boardImage : board.getImageSet()){
            log.info(boardImage);
        }
//        log.info(board.getImageSet());
        //Lazy 로딩은 현재 board 에서 image 리스트를 필요로 할때 검색
    }

    @Transactional
    @Commit
    @Test
    public void testModifyImages(){
        Optional<Board> result = boardRepository.findByIdWithImage(1L);
        Board board = result.orElseThrow();

        //기존의 첨부파일들은 삭제
        board.clearImages();

        //새로운 첨부파일들
        for (int i = 0; i < 2; i++){
            board.addImage(UUID.randomUUID().toString(),"file"+i+".jpg");
        }
        boardRepository.save(board);
    }

    @Test
    @Transactional
    @Commit
    public void testRemoveAll(){
        Long bno = 1L;
        replyRepository.deleteByBoard_bno(bno);
        boardRepository.deleteById(bno);
    }

    @Test
    public void testInsertAll(){
        for(int i = 1; i <= 100; i++){
            Board board = Board.builder()
                    .title("타이틀.."+i)
                    .content("내용.."+i)
                    .writer("글쓴이.." +i)
                    .build();
            for (int j = 0; j < 3; j++){
                if ( i % 5 == 0){
                    continue;
                }
                board.addImage(UUID.randomUUID().toString(),"file"+j+".jpg");
            }
            boardRepository.save(board);
        }
    }

    @Transactional
    @Test
    public void testModify(){
      Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());
      boardRepository.searchWithAll(null,null,pageable);
    }

    @Transactional
    @Test
    public void testSearchImageReplyCount(){
      Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());

      Page<BoardListAllDTO> result = boardRepository.searchWithAll(null,null,pageable);

      log.info("=====================");
      log.info(result.getTotalElements());

      result.getContent().forEach(boardListAllDTO -> log.info(boardListAllDTO));
    }
}
