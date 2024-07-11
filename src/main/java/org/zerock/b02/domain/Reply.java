package org.zerock.b02.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Reply", indexes ={ @Index(name = "idx_reply_board_bno",columnList = "board_bno:")})
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString //(exclude = "board")
public class Reply extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;
    //댓글여러개 > 게시글 1개
    @ManyToOne(fetch = FetchType.LAZY)
    private Board board; //게시글 참조

    private String replyText;
    private String replyer;
}
