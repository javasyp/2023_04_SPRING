package com.KoreaIT.syp.demo.vo;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.KoreaIT.syp.demo.util.Ut;

import lombok.Getter;

// 세션 정보를 확인하는 객체
public class Rq {
	@Getter
	private boolean isLogined;
	@Getter
	private int loginedMemberId;
	
	private HttpServletRequest req;
	private HttpServletResponse resp;
	private HttpSession session;
	
	public Rq(HttpServletRequest req, HttpServletResponse resp) {
		this.req = req;
		this.resp = resp;
		this.session = req.getSession();
		
		boolean isLogined = false;
		int loginedMemberId = 0;
		
		// 로그인되어 있을 때
		if (session.getAttribute("loginedMemberId") != null) {
			isLogined = true;
			loginedMemberId = (int) session.getAttribute("loginedMemberId");
		}
		
		this.isLogined = isLogined;
		this.loginedMemberId = loginedMemberId;
	}
	
	public void printHistoryBackJs(String msg) throws IOException {
		resp.setContentType("text/html; charset=UTF-8");
		print(Ut.jsHistoryBack("F-B", msg));
	}
	
	public void print(String str) {
		try {
			resp.getWriter().append(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void println(String str) {
		print(str + "\n");
	}
	
	// 로그인 시
	public void login(Member member) {
		session.setAttribute("loginedMemberId", member.getId());
	}
	
	// 로그아웃 시
	public void logout() {
		session.removeAttribute("loginedMemberId");
	}
	
	public String jsHistoryBackOnView(String msg) {
		req.setAttribute("msg", msg);
		req.setAttribute("historyBack", true);
		return "usr/common/js";
	}

	public String jsHistoryBack(String resultCode, String msg) {
		return Ut.jsHistoryBack(resultCode, msg);
	}

	public String jsReplace(String msg, String uri) {
		return Ut.jsReplace(msg, uri);
	}
}
