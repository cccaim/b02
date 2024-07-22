package org.zerock.b02.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.zerock.b02.domain.Member;
import org.zerock.b02.dto.MemberSecurityDTO;
import org.zerock.b02.repository.MemberRepository;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    // DB에서 찾기위해 리파지토리 주입
    private final MemberRepository memberRepository;

    //생성자 주입으로 등록된 암호화객체를 주입
    private final PasswordEncoder passwordEncoder;

    //시큐리티 인증에서 로그인하는 메소드
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("loadUserByUsername : " + username);

        Optional<Member> result = memberRepository.getWithRoles(username);
        if (result.isEmpty()) {
            throw new UsernameNotFoundException("username 을 찾을 수 없습니다.");
        }
        Member member = result.get();
        MemberSecurityDTO memberSecurityDTO =
                new MemberSecurityDTO(
                        member.getMid(),
                        member.getMpw(),
                        member.getEmail(),
                        member.isDel(),
                        false,
                        member.getRoleSet()
                                .stream().map(memberRole -> new SimpleGrantedAuthority("ROLE_"+memberRole.name())).collect(Collectors.toList())
        );
        log.info("loadUserByUsername : " + memberSecurityDTO);
        return  memberSecurityDTO;

//        //메모리 인증 테스트용
//        UserDetails userDetails = User.builder()
//                .username("user1")
//                .password(passwordEncoder.encode("1234"))
//                .authorities("ROLE_USER")
//                .build();

    }
}
