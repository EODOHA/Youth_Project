package com.youth.controller;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.youth.dto.IntroFormDto;
import com.youth.dto.IntroSearchDto;
import com.youth.entity.Intro;
import com.youth.service.IntroService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class IntroController {
	
	private final IntroService introService;
	
	@GetMapping(value = "/admin/intro/new")
	public String IntroForm(Model model) {
		model.addAttribute("introFormDto", new IntroFormDto());
		return "intro/introForm";
	}
	
	@PostMapping(value = "/admin/intro/new")
	public String introNew(@Valid IntroFormDto introFormDto, BindingResult bindingResult, Model model, @RequestParam("introImgFile") List<MultipartFile> introImgFileList) {
		
		if(bindingResult.hasErrors()) {
			return "intro/introForm";
		}
		
		if(introImgFileList.get(0).isEmpty() && introFormDto.getId() == null) {
			model.addAttribute("errorMessage", "첫 번째 설명 이미지는 필수 입력 값입니다.");
			return "intro/introForm";
		}
		
		try {
			introService.saveIntro(introFormDto, introImgFileList);
		} catch (Exception e) {
			model.addAttribute("errorMessage", "설명 등록 중 에러가 발생하였습니다.");
			return "intro/introForm";
		}
		
		return "redirect:/"; // 정상 등록 시 메인 페이지로 이동
	}
	
	@GetMapping(value = "/admin/intro/{introId}")
	public String introDtl(@PathVariable("introId") Long introId, Model model) {
		try {
			IntroFormDto introFormDto = introService.getIntroDtl(introId);
			model.addAttribute("introFormDto", introFormDto);
		} catch (EntityNotFoundException e) {
			model.addAttribute("errorMessage", "존재하지 않는 설명글입니다.");
			model.addAttribute("introFormDto", new IntroFormDto());
			return "intro/introForm";
		}
		
		return "intro/introForm";
	}
	
	@PostMapping(value = "/admin/intro/{introId}")
	public String introUpdate(@Valid IntroFormDto introFormDto, BindingResult bindingResult, @RequestParam("introImgFile") List<MultipartFile> introImgFileList, Model model) {
		if (bindingResult.hasErrors()) {
			return "intro/introForm";
		}
		
		if(introImgFileList.get(0).isEmpty() && introFormDto.getId() == null) {
			model.addAttribute("errorMessage", "첫 번째 인사말 이미지는 필수 입력 값입니다.");
			return "intro/introForm";
		}
		
		try {
			introService.updateIntro(introFormDto, introImgFileList);
		} catch (Exception e) {
			model.addAttribute("errorMessage", "인사말 수정 중 에러가 발생했습니다.");
			return "intro/introForm";
		}
		return "redirect:/";
	}
}
