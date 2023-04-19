package com.KoreaIT.syp.demo.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.KoreaIT.syp.demo.service.ArticleService;
import com.KoreaIT.syp.demo.util.Ut;
import com.KoreaIT.syp.demo.vo.Article;
import com.KoreaIT.syp.demo.vo.ResultData;

@Controller
public class UsrArticleController {
	@Autowired
	private ArticleService articleService;
	
	// 액션 메서드
	// 수정
	@RequestMapping("/usr/article/doModify")
	@ResponseBody
	public ResultData<Integer> doModify(HttpSession session, int id, String title, String body) {		// 세션 추가
		boolean isLogined = false;
		int loginedMemberId = 0;
		
		// 로그인되어 있을 때
		if (session.getAttribute("loginedMemberId") != null) {
			isLogined = true;
			loginedMemberId = (int) session.getAttribute("loginedMemberId");
		}
		// 로그아웃 상태일 때
		if (isLogined == false) {
			return ResultData.from("F-A", "로그인 후 이용해 주세요.");
		}
		
		Article article = articleService.getArticle(id);
		if (article == null) {
			return ResultData.from("F-1", Ut.f("%d번 글은 존재하지 않습니다.", id), "id", id);
		}
		
		ResultData actorCanModifyRd = articleService.actorCanModify(loginedMemberId, article);

		if (actorCanModifyRd.isFail()) {
			return actorCanModifyRd;
		}

		return articleService.modifyArticle(id, title, body);
	}
	
	// 삭제
	@RequestMapping("/usr/article/doDelete")
	@ResponseBody
	public ResultData<Integer> doDelete(HttpSession session, int id) {		// 세션 추가
		boolean isLogined = false;
		int loginedMemberId = 0;
		
		// 로그인되어 있을 때
		if (session.getAttribute("loginedMemberId") != null) {
			isLogined = true;
			loginedMemberId = (int) session.getAttribute("loginedMemberId");
		}
		// 로그아웃 상태일 때
		if (isLogined == false) {
			return ResultData.from("F-A", "로그인 후 이용해 주세요.");
		}
		
		Article article = articleService.getArticle(id);
		
		if (article == null) {
			return ResultData.from("F-1", Ut.f("%d번 글은 존재하지 않습니다.", id), "id", id);
		}
		
		// 권한 체크
		if (article.getMemberId() != loginedMemberId) {
			return ResultData.from("F-2", Ut.f("%d번 글에 대한 권한이 없습니다.", id));
		}

		articleService.deleteArticle(id);

		return ResultData.from("S-1", Ut.f("%d번 글을 삭제했습니다.", id), "id", id);
	}
	
	// 작성
	@RequestMapping("/usr/article/doWrite")
	@ResponseBody
	public ResultData<Article> doWrite(HttpSession session, String title, String body) {		// 제네릭 추가
		
		boolean isLogined = false;
		int loginedMemberId = 0;
		
		// 로그인되어 있을 때
		if (session.getAttribute("loginedMemberId") != null) {
			isLogined = true;
			loginedMemberId = (int) session.getAttribute("loginedMemberId");
		}
		// 로그아웃 상태일 때
		if (isLogined == false) {
			return ResultData.from("F-A", "로그인 후 이용해 주세요.");
		}
		
		if (Ut.empty(title)) {
			return ResultData.from("F-1", "제목을 입력해 주세요.");
		}
		
		if (Ut.empty(body)) {
			return ResultData.from("F-2", "내용을 입력해 주세요.");
		}
		
		ResultData<Integer> writeArticleRd = articleService.writeArticle(loginedMemberId, title, body);
		
		int id = (int) writeArticleRd.getData1();

		Article article = articleService.getArticle(id);
		
		return ResultData.newData(writeArticleRd, "article", article);
	}
	
	// 목록
	@RequestMapping("/usr/article/getArticles")
	public String getArticles(Model model) {
		List<Article> articles = articleService.articles();
		
		model.addAttribute("articles", articles);
		
		return "usr/article/list";
	}
	
	// 상세보기
	@RequestMapping("/usr/article/getArticle")
	@ResponseBody
	public ResultData<Article> getArticle(int id) {		// 제네릭 추가

		Article article = articleService.getArticle(id);

		if (article == null) {
			return ResultData.from("F-1", Ut.f("%d번 글은 존재하지 않습니다.", id));
		}

		return ResultData.from("S-1", Ut.f("%d번 게시물입니다.", id), "article", article);
	}
}