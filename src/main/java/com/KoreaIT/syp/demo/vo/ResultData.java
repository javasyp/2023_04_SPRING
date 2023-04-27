package com.KoreaIT.syp.demo.vo;

import lombok.Getter;

public class ResultData<DT> {			// 제네릭 추가
	@Getter
	private String resultCode;
	@Getter
	private String msg;
	@Getter
	private DT data1;
	@Getter
	private String data1Name;
	@Getter
	private Object data2;
	@Getter
	private String data2Name;
	
	// data1(article) 없을 때, 오버로딩
	public static <DT> ResultData<DT> from(String resultCode, String msg) {
		return from(resultCode, msg, null, null);
	}
	
	// 객체 생성해서 만족하는 데이터 있을 때
	public static <DT> ResultData<DT> from(String resultCode, String msg, String data1Name, DT data1) {
		ResultData<DT> rd = new ResultData<DT>();
		rd.resultCode = resultCode;
		rd.msg = msg;
		rd.data1Name = data1Name;
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
	
	public static <DT> ResultData<DT> newData(ResultData rd, String data1Name, DT newData) {
		return from(rd.getResultCode(), rd.getMsg(), data1Name, newData);
	}

	// 더 많은 정보를 보여주기 위함
	public void setData2(String data2Name, Object data2) {
		this.data2Name = data2Name;
		this.data2 = data2;
	}
}
