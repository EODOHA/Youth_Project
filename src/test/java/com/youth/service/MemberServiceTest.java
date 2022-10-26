package com.youth.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.transaction.Transactional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;

import com.youth.dto.MemberFormDto;
import com.youth.entity.Member;
import com.youth.service.MemberService;

@SpringBootTest
@Transactional
@TestPropertySource(locations="classpath:application-test.properties")
public class MemberServiceTest {

	@Autowired
	MemberService memberService;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	public Member createMember() {
		MemberFormDto memberFormDto = new MemberFormDto();
		memberFormDto.setEmail("test@test.com");
		memberFormDto.setName("테스트");
		memberFormDto.setOneline("한줄소개");
		memberFormDto.setPassword("12345");
		return Member.createMember(memberFormDto, passwordEncoder);
	}
	
//	@Test
//	@DisplayName("회원가입 테스트")
//	public void saveMemberTest() {
//		Member member = createMember();
//		Member savedMember = memberService.saveMember(member);
//		assertEquals(member.getEmail(), savedMember.getEmail());
//		assertEquals(member.getName(), savedMember.getName());
//		assertEquals(member.getOneline(), savedMember.getOneline());
//		assertEquals(member.getPassword(), savedMember.getPassword());
//	}
	
	@Test
	@DisplayName("중복 회원 가입 테스트")
	public void saveDuplicateMemberTest() {
		Member member1 = createMember();
		Member member2 = createMember();
		memberService.saveMember(member1);
		Throwable e = assertThrows(IllegalStateException.class, () -> {
			memberService.saveMember(member2);});
		assertEquals("이미 가입된 회원입니다.", e.getMessage());
	}
}
