package com.example.springsecurityfinal.domain.member;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberEntity {
    private String id;
    private String name;
    private String encodedPassword;
    private Integer age;
    private Role role;

    public MemberEntity(Member member, String encodedPassword) {
        this.id = member.getId();
        this.name = member.getName();
        this.encodedPassword = encodedPassword;
        this.age = member.getAge();
        this.role = member.getRole();
    }
}
