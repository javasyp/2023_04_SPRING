package com.KoreaIT.syp.demo.vo;

import lombok.Getter;

public class ResultData {
	@Getter
	private String resultCode;		// S-1 또는 F-1
	@Getter
	private String msg;		// 성공 또는 실패 메시지
	@Getter
	private Object data1;		// 데이터 (article 또는 member)
	
	// data1(article) 없을 때, 오버로딩
	public static ResultData from(String resultCode, String msg) {
		return from(resultCode, msg, null);
	}
	
	// 객체 생성해서 만족하는 데이터 있을 때
	public static ResultData from(String resultCode, String msg, Object data1) {
		ResultData rd = new ResultData();
		rd.resultCode = resultCode;
		rd.msg = msg;		
		rd.data1 = data1;
		
		return rd;
	}
	
	// is로 시작하면 자동으로 객체 내부 데이터 생성됨. 
	public boolean isSuccess() {		// success 객체 내부 데이터 생성됨
		// S로 시작은 성공
		return resultCode.startsWith("S-");
	}
	
	public boolean isFail() {			// fail 객체 내부 데이터 생성됨
		return isSuccess() == false;
	}
}
