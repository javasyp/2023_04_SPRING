package com.KoreaIT.syp.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Article {
	private int id;
	private String regDate;
	private String updateDate;
	private int memberId;
	private String title;
	private String body;
	private int hitCount;
	private int goodReactionPoint;
	private int badReactionPoint;
	
	// member 테이블의 nickname
	private String extra_writer;
	
	// 수정 및 삭제 권한
	private boolean actorCanDelete;
	private boolean actorCanModify;
}
