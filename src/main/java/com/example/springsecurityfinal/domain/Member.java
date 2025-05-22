package com.example.springsecurityfinal.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Member {
    private String id;
    private String name;
    private String password;
    private Integer age;
    @JsonSerialize(using = ToStringSerializer.class)
    private Role role;

    public Member(String id, String name, String password, Integer age, Role role) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.age = age;
        this.role = Role.valueOf(role.toString().toUpperCase());
    }

    @Override
    public String toString() {
        return id + "," + name + "," + password + "," + age + "," + role;
    }
}
