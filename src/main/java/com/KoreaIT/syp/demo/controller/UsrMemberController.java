package com.KoreaIT.syp.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.KoreaIT.syp.demo.service.MemberService;
import com.KoreaIT.syp.demo.util.Ut;
import com.KoreaIT.syp.demo.vo.Member;

@Controller
public class UsrMemberController {
	@Autowired
	private MemberService memberService;
	
	// 액션 메서드
	// 회원가입
	@RequestMapping("/usr/member/doJoin")
	@ResponseBody
	public Object doJoin(String loginId, String loginPw, String name, String nickname, String cellphoneNum, String email) {
		
		if (Ut.empty(loginId)) {
			return "아이디를 입력해 주세요.";
		}
		if (Ut.empty(loginPw)) {
			return "비밀번호를 입력해 주세요.";
		}
		if (Ut.empty(name)) {
			return "이름을 입력해 주세요.";
		}
		if (Ut.empty(nickname)) {
			return "닉네임을 입력해 주세요.";
		}
		if (Ut.empty(cellphoneNum)) {
			return "전화번호를 입력해 주세요.";
		}
		if (Ut.empty(email)) {
			return "이메일을 입력해 주세요.";
		}
		
		int id = memberService.join(loginId, loginPw, name, nickname, cellphoneNum, email);
		
		if (id == -1) {
			return "이미 사용 중인 아이디입니다!";
		}
		
		Member member = memberService.getMemberById(id);
		
		return member;
	}
}