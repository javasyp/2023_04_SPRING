package com.KoreaIT.syp.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.KoreaIT.syp.demo.vo.Article;

@Controller
public class UsrArticleController {
	int lastArticleId;
	List<Article> articles;
	
	// 1. 생성자
	public UsrArticleController() {
		lastArticleId = 0;
		articles = new ArrayList<>();
		
		makeTestData();
	}
	
	// 2. 서비스 메서드
	// 테스트 데이터
	private void makeTestData() {
		for (int i = 1; i <= 10; i++) {
			String title = "제목 " + i;
			String body = "내용 " + i;
			
			writeArticle(title, body);
		}
	}
	// 입력한 Id값과 article의 Id값 일치하는지?
	private Article getArticle(int id) {
		for (Article article : articles) {
			if (article.getId() == id) {	// getter 메소드
				return article;
			}
		}
		return null;
	}
	// 삭제
	private void deleteArticle(int id) {
		Article article = getArticle(id);
		articles.remove(article);
	}
	// 수정
	private void modifyArticle(int id, String title, String body) {
		Article article = getArticle(id);

		article.setTitle(title);	// setter 메소드
		article.setBody(body);		// setter 메소드
	}
	// 작성
	public Article writeArticle(String title, String body) {
		int id = lastArticleId + 1;

		Article article = new Article(id, title, body);
		articles.add(article);
		lastArticleId++;
		
		return article;
	}
	
	// 3. 액션 메서드
	// 수정
	@RequestMapping("/usr/article/doModify")
	@ResponseBody
	public Object doModify(int id, String title, String body) {		// Object
		Article article = getArticle(id);
		if (article == null) {
			return id + "번 글은 존재하지 않습니다";
		}

		modifyArticle(id, title, body);

		return article;
	}
	// 삭제
	@RequestMapping("/usr/article/doDelete")
	@ResponseBody
	public String doDelete(int id) {
		Article article = getArticle(id);
		if (article == null) {
			return id + "번 글은 존재하지 않습니다.";
		}

		deleteArticle(id);

		return id + "번 글을 삭제했습니다.";
	}
	// 작성
	@RequestMapping("/usr/article/doAdd")
	@ResponseBody
	public Article doAdd(String title, String body) {
		
		Article article = writeArticle(title, body);
		
		return article;
	}
	// 목록
	@RequestMapping("/usr/article/getArticles")
	@ResponseBody
	public List<Article> getArticles() {
		
		return articles;
	}
	// 상세보기
	@RequestMapping("/usr/article/getArticle")
	@ResponseBody
	public Object getArticleAction(int id) {		// Object

		Article article = getArticle(id);

		if (article == null) {
			return id + "번 글은 존재하지 않습니다";
		}

		return article;
	}
}