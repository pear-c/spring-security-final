package com.example.springsecurityfinal.repository;

import com.example.springsecurityfinal.domain.member.MemberEntity;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    boolean existsById(String id);
    void save(MemberEntity memberEntity);
    Optional<MemberEntity> findById(String id);
    List<MemberEntity> findAll();
}
