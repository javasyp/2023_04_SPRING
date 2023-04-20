package com.KoreaIT.syp.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.KoreaIT.syp.demo.vo.Article;

@Mapper
public interface ArticleRepository {

	public void writeArticle(int memberId, String title, String body);

	public List<Article> getArticles();
	
	public List<Article> getForPrintArticles();

	public Article getArticle(int id);
	
	public Article getForPrintArticle(int id);

	public void deleteArticle(int id);

	public void modifyArticle(int id, String title, String body);

	public int getLastInsertId();
	
}
