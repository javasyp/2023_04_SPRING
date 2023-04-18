package com.KoreaIT.syp.demo.vo;

import lombok.Getter;

public class ResultData<DT> {			// 제네릭 추가
	@Getter
	private String resultCode;
	@Getter
	private String msg;
	@Getter
	private DT data1;
	
	// data1(article) 없을 때, 오버로딩
	public static <DT> ResultData<DT> from(String resultCode, String msg) {
		return from(resultCode, msg, null);
	}
	
	// 객체 생성해서 만족하는 데이터 있을 때
	public static <DT> ResultData<DT> from(String resultCode, String msg, DT data1) {
		ResultData<DT> rd = new ResultData<DT>();
		rd.resultCode = resultCode;
		rd.msg = msg;		
		rd.data1 = data1;
		
		return rd;
	}
	
	// is로 시작하는 메서드를 생성하면 자동으로 객체 내부 데이터가 생성된다.
	public boolean isSuccess() {		// success 객체 내부 데이터 생성됨
		// S로 시작은 성공
		return resultCode.startsWith("S-");
	}
	
	public boolean isFail() {			// fail 객체 내부 데이터 생성됨
		return isSuccess() == false;
	}
	
	// 회원가입 성공 시
	public static <DT> ResultData<DT> newData(ResultData joinRd, DT newData) {
		return from(joinRd.getResultCode(), joinRd.getMsg(), newData);
	}
}
