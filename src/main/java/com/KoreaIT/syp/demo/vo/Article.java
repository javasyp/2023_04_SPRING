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
	
	// member 테이블의 nickname
	private String extra_writer;
	
	// reactionPoint 테이블의 `point`
	private int extra_sumReactionPoint;		// 합계
	private int extra_goodReactionPoint;	// 좋아요 수
	private int extra_badReactionPoint;		// 싫어요 수
	
	// 수정 및 삭제 권한
	private boolean actorCanDelete;
	private boolean actorCanModify;
}
