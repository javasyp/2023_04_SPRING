package com.KoreaIT.syp.demo.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.KoreaIT.syp.demo.vo.Article;

@Component
public class ArticleRepository {
	
	private int lastArticleId;
	private List<Article> articles;
	
	// 생성자
	public ArticleRepository() {
		lastArticleId = 0;
		articles = new ArrayList<>();
	}
	
	// 테스트 데이터
	public void makeTestData() {
		for (int i = 1; i <= 10; i++) {
			String title = "제목 " + i;
			String body = "내용 " + i;
			
			writeArticle(title, body);
		}
	}
	
	// 작성
	public Article writeArticle(String title, String body) {
		int id = lastArticleId + 1;

		Article article = new Article(id, title, body);
		articles.add(article);
		lastArticleId++;
		
		return article;
	}
	
	// 객체 데이터 가져오기
	public Article getArticle(int id) {
		for (Article article : articles) {
			if (article.getId() == id) {	// getter 메소드
				return article;
			}
		}
		return null;
	}
	
	// 삭제
	public void deleteArticle(int id) {
		Article article = getArticle(id);
		articles.remove(article);
	}
	
	// 수정
	public void modifyArticle(int id, String title, String body) {
		Article article = getArticle(id);

		article.setTitle(title);	// setter 메소드
		article.setBody(body);		// setter 메소드
	}
	
	public List<Article> getArticles() {
		return articles;
	}
}
