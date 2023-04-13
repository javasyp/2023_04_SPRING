package com.KoreaIT.syp.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
	
}