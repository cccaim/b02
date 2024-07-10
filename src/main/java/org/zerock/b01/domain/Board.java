package org.zerock.b01.domain;

import jakarta.persistence.*;
import lombok.*;

//entity 는 테이블과 같은 클래스
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bno;       //보드번호 (기본키 , 자동증가)

    @Column(length = 500, nullable = false)
    private String title;   //제목

    @Column(length = 2000, nullable = false)
    private String content; //내용

    @Column(length = 50, nullable = false)
    private String writer;  //글쓴이

    public void change(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
