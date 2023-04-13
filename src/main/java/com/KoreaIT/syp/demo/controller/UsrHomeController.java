package com.KoreaIT.syp.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.Data;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Controller
public class UsrHomeController {
	
	@RequestMapping("/usr/home/getInt")
	@ResponseBody
	public int getInt() {
		return 10;
	}
	
	@RequestMapping("/usr/home/getString")
	@ResponseBody
	public String getString() {
		return "안녕";
	}
	
	@RequestMapping("/usr/home/getFloat")
	@ResponseBody
	public float getFloat() {
		return 10.5f;
	}
	
	@RequestMapping("/usr/home/getDouble")
	@ResponseBody
	public double getDouble() {
		return 10.5;
	}
	
	@RequestMapping("/usr/home/getBoolean")
	@ResponseBody
	public boolean getBoolean() {
		return true;
	}
	
	@RequestMapping("/usr/home/getChar")
	@ResponseBody
	public char getChar() {
		return 'a';
	}
	
	@RequestMapping("/usr/home/getMap")
	@ResponseBody
	public Map<String, Object> getMap() {
		Map<String, Object> map = new HashMap<>();
		map.put("철수 나이", 22);
		map.put("영희 나이", 20);
		
		return map;		// {}
	}
	
	@RequestMapping("/usr/home/getList")
	@ResponseBody
	public List<Object> getList() {
		List<Object> list = new ArrayList<>();
		list.add("철수 나이");
		list.add(22);
		
		return list;	// []
	}
	
	@RequestMapping("/usr/home/getArticle")
	@ResponseBody
	public Article getArticle() {
		Article article = new Article(1, "제목1");
		
		return article;
	}
	
	@RequestMapping("/usr/home/getArticles")
	@ResponseBody
	public List<Article> getArticles() {
		Article article1 = new Article(1, "제목1");
		Article article2 = new Article(2, "제목2");

		List<Article> articles = new ArrayList<>();
		articles.add(article1);
		articles.add(article2);

		return articles;
	}
}

// 클래스 내부 private 변수를 전체 사용하려면 @Data 메소드를 써준다.
@Data
// 생성자를 만들지 않아도 된다.
@AllArgsConstructor		// Article(int, String)
@NoArgsConstructor		// Article()
class Article {
	// 외부에서 private 변수를 쓰려면 @Getter 메소드를 사용해야 한다.
//	@Getter
	private int id;
//	@Getter
	private String title;
	
	// @Getter 메소드가 없으면 이렇게 써줘야 한다.
//	public int getId() {
//		return id;
//	}
	
	// @AllArgsConstructor 메소드 사용 시 필요없음
//	public Article() {
//		// this 생략
//		id = 1;
//		title = "제목";
//	}
}