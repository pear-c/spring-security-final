package com.example.springsecurityfinal.model;

public enum Role {
    ADMIN, MEMBER, GOOGLE;

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
