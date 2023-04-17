package com.KoreaIT.syp.demo.vo;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Member {
	private int id;
	private String regDate;
	private String updateDate;
	private String loginId;
	private String loginPw;
	private int autoLevel;
	private String name;
	private String nickname;
	private String cellphoneNum;
	private String email;
	private boolean delStatus;
	private String delDate;
}