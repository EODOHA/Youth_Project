package com.youth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.youth.dto.LectureFormDto;

@Controller
public class LectureController {

	@GetMapping(value = "/admin/lecture/new")
	public String lectureForm(Model model) {
		model.addAttribute("lectureFormDto", new LectureFormDto());
		return "lecture/lectureForm";
	}
}
