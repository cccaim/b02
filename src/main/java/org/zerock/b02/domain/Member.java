package org.zerock.b02.domain;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "roleSet")
public class Member extends BaseEntity {

    @Id
    private String mid;
    private String mpw;
    private String email;

    private boolean del;   //탈퇴

    private boolean social; //쇼셜로 로그인

    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private Set<MemberRole> roleSet = new HashSet<>();

    public  void changePassword(String password) {
        this.mpw = password;
    }
    public void changeEmail(String email) {
        this.email = email;
    }
    public void changeDel(boolean del) {
        this.del = del;
    }
    public void addRole(MemberRole role) {
        this.roleSet.add(role);
    }
    public void clearRoles(){
        this.roleSet.clear();
    }
    public void changeSocial(boolean social) {
        this.social = social;
    }
}
