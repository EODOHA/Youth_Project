package com.youth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.youth.dto.QnaBoardRequestDto;
import com.youth.repository.QnaBoardRepository;
import com.youth.service.QnaBoardService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class QnaBoardController {
	
	private final QnaBoardService qbService;
	
	private final QnaBoardRepository qbRepository;
	
	@GetMapping("/qnaboard/list")
	public String getQnaBoardListPage(Model model, @RequestParam(required = false, defaultValue = "0")
										Integer page, @RequestParam(required = false, defaultValue = "5")
										Integer size) throws Exception{
		
		try {
			model.addAttribute("resultMap", qbService.findAll(page, size));
		} catch(Exception e) {
			throw new Exception(e.getMessage());
		}
		return "/qnaboard/list";
	}
	
	@GetMapping("/qnaboard/write")
	public String getQnaBoardWritePage(Model model, QnaBoardRequestDto qbRequestDto) throws Exception{
		return "/qnaboard/write";
	}
	
	@GetMapping("/qnaboard/view")
	public String getQnaBoardViewPage(Model model, QnaBoardRequestDto qbRequestDto) throws Exception {
		try {
			if (qbRequestDto.getQboardno() != null) {
				model.addAttribute("info", qbService.findById(qbRequestDto.getQboardno()));
			}
		} catch(Exception e) {
			throw new Exception(e.getMessage());
		}
		return "/qnaboard/view";
	}
	
	@PostMapping("/qnaboard/write/action")
//	@PreAuthorize("hasRole('USER')")
	public String QnaBoardWriteAction(Model model, QnaBoardRequestDto qbRequestDto) throws Exception{
		
		try {
			Long result = qbService.save(qbRequestDto);
			
			if(result < 0) {
				throw new Exception("#Exception qnaboardWriteAction!");
			}
		} catch(Exception e) {
			throw new Exception(e.getMessage());
		}
		return "redirect:/qnaboard/list";
	}
	
	@PostMapping("/qnaboard/view/action")
	public String qnaboardViewAction(Model model, QnaBoardRequestDto qbRequestDto) throws Exception{
		try {
			int result = qbService.updateQnaBoard(qbRequestDto);
			
			if (result < 1) {
				throw new Exception("#Exception qnaboardViewAction!");
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return "redirect:/qnaboard/list";
	}
	
	@PostMapping("/qnaboard/view/delete")
	public String qnaboardViewDeleteAction(Model model, @RequestParam() Long qboardno) throws Exception{
		try {
			qbService.deleteById(qboardno);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return "redirect:/qnaboard/list";
	}
	
	@PostMapping("/qnaboard/delete")
	public String qnaboardDeleteAction(Model model, @RequestParam() Long[] deleteQboardno) throws Exception{
		try {
			qbService.deleteAll(deleteQboardno);
		} catch(Exception e) {
			throw new Exception(e.getMessage());
		}
		return "redirect:/qnaboard/list";
	}
	

}