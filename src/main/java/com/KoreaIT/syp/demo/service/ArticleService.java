package com.KoreaIT.syp.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.KoreaIT.syp.demo.repository.ArticleRepository;
import com.KoreaIT.syp.demo.util.Ut;
import com.KoreaIT.syp.demo.vo.Article;
import com.KoreaIT.syp.demo.vo.ResultData;

@Service
public class ArticleService {
	
	@Autowired
	private ArticleRepository articleRepository;

	public ArticleService(ArticleRepository articleRepository) {
		this.articleRepository = articleRepository;
	}
	
	// 서비스 메서드
	public ResultData writeArticle(String title, String body) {		// int -> ResultData
		// 서비스에서 ResultData로 처리
		articleRepository.writeArticle(title, body);
		
		int id = articleRepository.getLastInsertId();
		
		return ResultData.from("S-1", Ut.f("%d번 글이 생성되었습니다.", id), id);
	}

	public Article getArticle(int id) {
		return articleRepository.getArticle(id);
	}

	public void deleteArticle(int id) {
		articleRepository.deleteArticle(id);
	}

	public void modifyArticle(int id, String title, String body) {
		articleRepository.modifyArticle(id, title, body);
	}

	public List<Article> articles() {
		return articleRepository.getArticles();
	}
}
