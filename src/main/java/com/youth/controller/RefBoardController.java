package com.youth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.youth.dto.RefBoardRequestDto;
import com.youth.service.RefBoardService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class RefBoardController {

	private final RefBoardService refBoardService;
	
	@GetMapping("/refboard/list")
	public String getRefBoardListPage(Model model, 
									  @RequestParam(required = false, defaultValue = "0") Integer page,
									  @RequestParam(required = false, defaultValue = "5") Integer size) throws Exception {
		try {
			model.addAttribute("refResultMap", refBoardService.findAll(page, size));
		} catch(Exception e) {
			throw new Exception(e.getMessage());
		}
		
		return "/refboard/list";
	}
	
	@GetMapping("/refboard/write")
	public String getRefBoardWritePage(Model model, RefBoardRequestDto refBoardRequestDto) {
		return "/refboard/write";
	}
	
	@GetMapping("/refboard/view")
	public String getRefBoardViewPage(Model model, RefBoardRequestDto refBoardRequestDto) throws Exception {
		try {
			if(refBoardRequestDto.getRefId() != null) {
				model.addAttribute("refInfo", refBoardService.findById(refBoardRequestDto.getRefId()));
			}
		} catch(Exception e) {
			throw new Exception(e.getMessage());
		}
		return "/refboard/view";
	}
	
	@PostMapping("/refboard/write/action")
	public String refBoardWriteAction(Model model, RefBoardRequestDto refBoardRequestDto) throws Exception {
		try {
			Long refResult = refBoardService.save(refBoardRequestDto);
			
			if(refResult < 0) {
				throw new Exception("#Exception refBoardWriteAction!");
			}
		} catch(Exception e) {
			throw new Exception(e.getMessage());
		}
		
		return "redirect:/refboard/list";
	}
	
	@PostMapping("/refboard/view/action")
	public String refBoardViewAction(Model model, RefBoardRequestDto refBoardRequestDto) throws Exception {
		try {
			int refResult = refBoardService.updateRefBoard(refBoardRequestDto);
			
			if(refResult < 1) {
				throw new Exception("#Exception refBoardViewAction!");
			}
		} catch(Exception e) {
			throw new Exception(e.getMessage());
		}
		
		return "redirect:/refboard/list";
	}
	
	@PostMapping("/refboard/view/delete")
	public String refBoardViewDeleteAction(Model model, @RequestParam() Long id) throws Exception {
		
		try {
			refBoardService.deleteById(id);
		} catch(Exception e) {
			throw new Exception(e.getMessage());
		}
		return "redirect:/refboard/list";
	}
	
	@PostMapping("/refboard/delete")
	public String refBoardDeleteAction(Model model, @RequestParam() Long[] deleteId) throws Exception {
		try {
			refBoardService.deleteAll(deleteId);
		} catch(Exception e) {
			throw new Exception(e.getMessage());
		}
		
		return "redirect:/refboard/list";
	}
}