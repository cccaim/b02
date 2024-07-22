package org.zerock.b02.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
@Log4j2
@RequiredArgsConstructor
public class MemberController {

    //시큐리티 로그인주소 /member/login 으로 커스텀 로그인페이지 이동
    @GetMapping("/login")
    public void loginGET(String error, String logout) {
        log.info("login GET .....");
        log.info("로그아웃 : " + logout );
        if(logout != null){
            log.info("유저 로그아웃....");
        }
    }
}
