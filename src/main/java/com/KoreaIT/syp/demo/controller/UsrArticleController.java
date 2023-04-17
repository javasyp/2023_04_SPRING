package com.KoreaIT.syp.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.KoreaIT.syp.demo.service.ArticleService;
import com.KoreaIT.syp.demo.vo.Article;

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
	public Article doWrite(String title, String body) {
		
		int id = articleService.writeArticle(title, body);

		Article article = articleService.getArticle(id);
		
		return article;
	}
	
	// 목록
	@RequestMapping("/usr/article/getArticles")
	@ResponseBody
	public List<Article> getArticles() {
		
		return articleService.articles();		// articles -> articles() 메소드 생성
	}
	
	// 상세보기
	@RequestMapping("/usr/article/getArticle")
	@ResponseBody
	public Object getArticle(int id) {		// Object

		Article article = articleService.getArticle(id);

		if (article == null) {
			return id + "번 글은 존재하지 않습니다";
		}

		return article;
	}
}