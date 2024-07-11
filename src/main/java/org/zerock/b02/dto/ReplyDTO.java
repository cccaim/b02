package org.zerock.b02.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReplyDTO {
    private Long rno;               //댓글번호
    @NotNull
    private Long bno;               //게시글번호
    @NotEmpty
    private String replyText;       //댓글내용
    @NotEmpty
    private String replyer;         //게시자
    private LocalDateTime regDate, modDate; //등록일자,수정일자
}
