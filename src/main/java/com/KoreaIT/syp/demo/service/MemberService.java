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
		memberRepository.join(loginId, loginPw, name, nickname, cellphoneNum, email);
		
		return memberRepository.getLastInsertId();
	}

	public Member getMemberById(int id) {
		return memberRepository.getMemberById(id);
	}
}
