package com.KoreaIT.syp.demo.service;

import org.springframework.stereotype.Service;

import com.KoreaIT.syp.demo.repository.MemberRepository;
import com.KoreaIT.syp.demo.vo.Member;

@Service
public class MemberService {
	private MemberRepository memberRepository;
	
	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}
	
	// 서비스 메서드
	public int join(String loginId, String loginPw, String name, String nickname, String cellphoneNum, String email) {
		
		// 아이디 중복 체크
		Member existsMember = getMemberByLoginId(loginId);

		if (existsMember != null) {
			return -1;
		}
		
		// 이름 + 이메일 중복 체크
		existsMember = getMemberByNameAndEmail(name, email);

		if (existsMember != null) {
			return -2;
		}
		
		memberRepository.join(loginId, loginPw, name, nickname, cellphoneNum, email);
		
		return memberRepository.getLastInsertId();
	}
	
	// 이름 + 이메일 중복 체크
	private Member getMemberByNameAndEmail(String name, String email) {
		return memberRepository.getMemberByNameAndEmail(name, email);
	}

	// 아이디 중복 체크
	private Member getMemberByLoginId(String loginId) {
		return memberRepository.getMemberByLoginId(loginId);
	}

	public Member getMemberById(int id) {
		return memberRepository.getMemberById(id);
	}
	
}
