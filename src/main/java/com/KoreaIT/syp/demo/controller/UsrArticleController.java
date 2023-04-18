package com.KoreaIT.syp.demo.controller;

import java.util.List;

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
	
//	public UsrArticleController() {
//		articleService = new ArticleService();	// @Autowired 사용 시 필요없음.
//	}
	
	// 액션 메서드
	// 수정
	@RequestMapping("/usr/article/doModify")
	@ResponseBody
	public Object doModify(int id, String title, String body) {		// Object
		Article article = articleService.getArticle(id);
		if (article == null) {
			return id + "번 글은 존재하지 않습니다";
		}

		articleService.modifyArticle(id, title, body);

		return article;
	}
	
	// 삭제
	@RequestMapping("/usr/article/doDelete")
	@ResponseBody
	public String doDelete(int id) {
		Article article = articleService.getArticle(id);
		if (article == null) {
			return id + "번 글은 존재하지 않습니다.";
		}

		articleService.deleteArticle(id);

		return id + "번 글을 삭제했습니다.";
	}
	
	// 작성
	@RequestMapping("/usr/article/doWrite")
	@ResponseBody
	public ResultData doWrite(String title, String body) {		// Article -> ResultData
		
		if (Ut.empty(title)) {
			return ResultData.from("F-1", "제목을 입력해 주세요.");
		}
		
		if (Ut.empty(body)) {
			return ResultData.from("F-1", "내용을 입력해 주세요.");
		}
		
		// 서비스에서 처리한 내용 뿌려주기
		ResultData writeArticleRd = articleService.writeArticle(title, body);
		
		int id = (int) writeArticleRd.getData1();

		Article article = articleService.getArticle(id);
		
		return ResultData.from(writeArticleRd.getResultCode(), writeArticleRd.getMsg(), article);
	}
	
	// 목록
	@RequestMapping("/usr/article/getArticles")
	@ResponseBody
	public ResultData getArticles() {		// List<Article> -> ResultData
		
		List<Article> articles = articleService.articles();
		
		return ResultData.from("S-1", "Article List", articles);
	}
	
	// 상세보기
	@RequestMapping("/usr/article/getArticle")
	@ResponseBody
	public ResultData getArticle(int id) {		// Object -> ResultData

		Article article = articleService.getArticle(id);

		if (article == null) {
			return ResultData.from("F-1", Ut.f("%d번 글은 존재하지 않습니다.", id));
		}

		return ResultData.from("S-1", Ut.f("%d번 게시물입니다.", id), article);
	}
}