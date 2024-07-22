package org.zerock.b02.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

  // 기본페이지를 리스트로 설정
  @GetMapping("/")
  public String home() {
    return "redirect:/board/list";
  }
}
