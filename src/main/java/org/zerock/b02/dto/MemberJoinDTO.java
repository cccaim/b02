package org.zerock.b02.dto;

import lombok.Data;

@Data
public class MemberJoinDTO {

  private String mid;
  private String mpw;
  private String email;
  private String del;     // 회원 탈퇴? yes,no
  private String social;  // 소셜계정(카카오,네이버,구글)로 가입? yes no
}
