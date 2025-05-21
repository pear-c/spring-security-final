package com.example.springsecurityfinal.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Role {
    ADMIN, MEMBER, GOOGLE;

    @JsonCreator
    public static Role fromString(String str){
        for (Role role : Role.values()) {
            if (role.name().equalsIgnoreCase(str)) {
                return role;
            }
        }
        //default
        return MEMBER;
    }

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
