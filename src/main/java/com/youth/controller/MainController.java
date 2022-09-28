package com.youth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
	
	@GetMapping(value = "/")
	public String main() {
		return "mainPage";
	}
	
	@GetMapping(value = "/admin/homeUpdate") // 앞에 '/' 절대 경로 써주면 안 됨 
	public String homePage() {
		return "/adminPage/homeUpdate";
	}
	
	@GetMapping(value = "introPage")
	public String introPage() {
		return "/intro/introPage";
	}
}