package org.zerock.b02.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.zerock.b02.domain.Member;
import org.zerock.b02.domain.MemberRole;

import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class MemberRepositoryTests {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void insertMembers(){
        //총 100명의 유저 생성하여 추가하기
        IntStream.range(1, 100).forEach(i -> {
            Member member = Member.builder()
                    .mid("member"+i)
                    .mpw(passwordEncoder.encode("1234"))
                    .email("email"+i+"@aaa.bbb")
                    .build();
            member.addRole(MemberRole.USER); //권한 추가
            if(i>=90){
                member.addRole(MemberRole.ADMIN); //i가 90이상이면 관리자 권한도 추가
            }
            memberRepository.save(member); //DB에 저장
        });
    }
}
