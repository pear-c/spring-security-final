package com.example.springsecurityfinal.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberEntity {
    private String id;
    private String name;
    private String password;
    private Integer age;
    @JsonSerialize(using = ToStringSerializer.class)
    private Role role;

    public MemberEntity(Member member, String encodedPassword) {
        this.id = member.getId();
        this.name = member.getName();
        this.password = encodedPassword;
        this.age = member.getAge();
        this.role = member.getRole();
    }
}
