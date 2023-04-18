package com.KoreaIT.syp.demo.controller;

import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.KoreaIT.syp.demo.service.MemberService;
import com.KoreaIT.syp.demo.util.Ut;
import com.KoreaIT.syp.demo.vo.Article;
import com.KoreaIT.syp.demo.vo.Member;
import com.KoreaIT.syp.demo.vo.ResultData;

@Controller
public class UsrMemberController {
	@Autowired
	private MemberService memberService;

	// 액션 메서드
	// 로그인
	@RequestMapping("/usr/member/doLogin")
	@ResponseBody
	public ResultData<Member> doLogin(HttpSession session, String loginId, String loginPw) throws Exception {
		
		boolean isLogined = false;
		
		// 로그인 상태일 때
		if (session.getAttribute("loginedMemberId") != null) {
			isLogined = true;
		}

		if (isLogined) {
			return ResultData.from("F-5", "현재 로그인 상태입니다.");
		}
		
		if (Ut.empty(loginId)) {
			return ResultData.from("F-1", "아이디를 입력해 주세요.");
		}
		
		if (Ut.empty(loginPw)) {
			return ResultData.from("F-2", "비밀번호를 입력해 주세요.");
		}
		
		Member member = memberService.getMemberByLoginId(loginId);
		
		if (member == null) {
			return ResultData.from("F-3", Ut.f("%s은(는) 존재하지 않는 아이디입니다.", loginId));
		}
		
		if (member.getLoginPw().equals(loginPw) == false) {
			return ResultData.from("F-4", "비밀번호가 일치하지 않습니다.");
		}
		
		session.setAttribute("loginedMemberId", member.getId());
		
		return ResultData.from("S-1", Ut.f("%s 님 환영합니다.", member.getName()), member);
	}
	
	// 회원가입
	@RequestMapping("/usr/member/doJoin")
	@ResponseBody
	public ResultData<Member> doJoin(String loginId, String loginPw,
			String name, String nickname, String cellphoneNum, String email) { // 제네릭 추가

		if (Ut.empty(loginId)) {
			return ResultData.from("F-1", "아이디를 입력해 주세요.");
		}
		if (Ut.empty(loginPw)) {
			return ResultData.from("F-2", "비밀번호를 입력해 주세요.");
		}
		if (Ut.empty(name)) {
			return ResultData.from("F-3", "이름을 입력해 주세요.");
		}
		if (Ut.empty(nickname)) {
			return ResultData.from("F-4", "닉네임을 입력해 주세요.");
		}
		if (Ut.empty(cellphoneNum)) {
			return ResultData.from("F-5", "전화번호를 입력해 주세요.");
		}
		if (Ut.empty(email)) {
			return ResultData.from("F-6", "이메일을 입력해 주세요.");
		}

		ResultData<Integer> joinRd = memberService.join(loginId, loginPw, name,
				nickname, cellphoneNum, email);

		// 중복일 때
		if (joinRd.isFail()) {
			return (ResultData) joinRd;
		}

		// joinRd에 저장된 data1(id) 값
		Member member = memberService.getMemberById(joinRd.getData1());

		return ResultData.newData(joinRd, member);
	}
}