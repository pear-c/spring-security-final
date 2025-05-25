package com.example.springsecurityfinal.service.impl;

import com.example.springsecurityfinal.domain.member.Member;
import com.example.springsecurityfinal.domain.member.MemberEntity;
import com.example.springsecurityfinal.domain.member.Role;
import com.example.springsecurityfinal.exception.MemberAlreadyExistsException;
import com.example.springsecurityfinal.repository.MemberRepository;
import com.example.springsecurityfinal.service.impl.MemberServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

class MemberServiceImplTest {

    @Mock
    MemberRepository memberRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    MemberServiceImpl memberService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("회원 생성 성공")
    void createMember_success() {
        // given
        Member member = new Member("kim", "김철수", "1234", 25, Role.MEMBER);
        given(memberRepository.existsById("kim")).willReturn(false);
        given(passwordEncoder.encode("1234")).willReturn("encoded1234");

        // when
        memberService.createMember(member);

        // then
        ArgumentCaptor<MemberEntity> captor = ArgumentCaptor.forClass(MemberEntity.class);
        verify(memberRepository).save(captor.capture());
        assertThat(captor.getValue().getId()).isEqualTo("kim");
        assertThat(captor.getValue().getEncodedPassword()).isEqualTo("encoded1234");
    }

    @Test
    @DisplayName("이미 존재하는 ID로 회원 생성 시 예외 발생")
    void createMember_duplicate() {
        // given
        Member member = new Member("kim", "김철수", "1234", 25, Role.MEMBER);
        given(memberRepository.existsById("kim")).willReturn(true);

        // expect
        assertThatThrownBy(() -> memberService.createMember(member))
                .isInstanceOf(MemberAlreadyExistsException.class);
    }

    @Test
    @DisplayName("회원 조회 성공")
    void getMember_success() {
        // given
        Member member = new Member("kim", "김철수", "1234", 25, Role.MEMBER);
        MemberEntity entity = new MemberEntity(member, "encoded");
        given(memberRepository.findById("kim")).willReturn(Optional.of(entity));

        // when
        Member result = memberService.getMember("kim");

        // then
        assertThat(result.getId()).isEqualTo("kim");
        assertThat(result.getName()).isEqualTo("김철수");
        assertThat(result.getPassword()).isEqualTo("encoded");
    }

    @Test
    @DisplayName("모든 회원 리스트 조회")
    void getMembers() {
        // given
        Member member1 = new Member("kim", "김철수", "1234", 25, Role.MEMBER);
        Member member2 = new Member("lee", "이영희", "22", 25, Role.ADMIN);

        MemberEntity e1 = new MemberEntity(member1, "encoded");
        MemberEntity e2 = new MemberEntity(member2, "encoded");
        given(memberRepository.findAll()).willReturn(Arrays.asList(e1, e2));

        // when
        List<Member> result = memberService.getMembers();

        // then
        assertThat(result).hasSize(2);
        assertThat(result).extracting("id").containsExactly("kim", "lee");
    }

    @Test
    @DisplayName("MemberEntity 조회")
    void getMemberEntity() {
        // given
        Member member = new Member("kim", "김철수", "1234", 25, Role.MEMBER);
        MemberEntity entity = new MemberEntity(member, "encoded");
        given(memberRepository.findById("kim")).willReturn(Optional.of(entity));

        // when
        MemberEntity result = memberService.getMemberEntity("kim");

        // then
        assertThat(result.getId()).isEqualTo("kim");
    }
}
