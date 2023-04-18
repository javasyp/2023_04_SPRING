package com.KoreaIT.syp.demo.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
	public ResultData<Integer> doModify(int id, String title, String body) {		// Object -> ResultData, 제네릭 추가
		Article article = articleService.getArticle(id);
		if (article == null) {
			return ResultData.from("F-1", Ut.f("%d번 글은 존재하지 않습니다.", id), id);
		}

		articleService.modifyArticle(id, title, body);

		return ResultData.from("F-1", Ut.f("%d번 글을 수정했습니다.", id), id);
	}
	
	// 삭제
	@RequestMapping("/usr/article/doDelete")
	@ResponseBody
	public ResultData<Integer> doDelete(int id) {		// String -> ResultData, 제네릭 추가
		Article article = articleService.getArticle(id);
		if (article == null) {
			return ResultData.from("F-1", Ut.f("%d번 글은 존재하지 않습니다.", id), id);
		}

		articleService.deleteArticle(id);

		return ResultData.from("F-1", Ut.f("%d번 글을 삭제했습니다.", id), id);
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
		
		return ResultData.newData(writeArticleRd, article);
	}
	
	// 목록
	@RequestMapping("/usr/article/getArticles")
	@ResponseBody
	public ResultData<List<Article>> getArticles() {		// 제네릭 추가
		
		List<Article> articles = articleService.articles();
		
		return ResultData.from("S-1", "Article List", articles);
	}
	
	// 상세보기
	@RequestMapping("/usr/article/getArticle")
	@ResponseBody
	public ResultData<Article> getArticle(int id) {		// 제네릭 추가

		Article article = articleService.getArticle(id);

		if (article == null) {
			return ResultData.from("F-1", Ut.f("%d번 글은 존재하지 않습니다.", id));
		}

		return ResultData.from("S-1", Ut.f("%d번 게시물입니다.", id), article);
	}
}