package org.zerock.b02.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zerock.b02.domain.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {

    //유저 id로 유저와 권한을 가져옴 이때 권한은 lazy 로딩이므로 즉시 가져올수 있도록 EntityGraph 사용
    @EntityGraph(attributePaths = "roleSet")
    @Query("select m from Member m where m.mid = :mid and m.social = false")
    Optional<Member> getWithRoles(String mid);
}
