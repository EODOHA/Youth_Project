package com.youth.service;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.youth.dto.RefBoardRequestDto;
import com.youth.dto.RefBoardResponseDto;
import com.youth.entity.RefBoard;
import com.youth.repository.RefBoardRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class RefBoardService {

	private final RefBoardRepository refBoardRepository;
	
	@Transactional
	public Long save(RefBoardRequestDto refBoardSaveDto) {
		return refBoardRepository.save(refBoardSaveDto.toEntity()).getRefId();
	}
	
	@Transactional
	public HashMap<String, Object> findAll(Integer page, Integer size) {
		
		HashMap<String, Object> refResultMap = new HashMap<String, Object>();
		
		Page<RefBoard> refList = refBoardRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "refRegisterTime")));
		
		refResultMap.put("refList", refList.stream().map(RefBoardResponseDto::new).collect(Collectors.toList()));
		refResultMap.put("refPaging", refList.getPageable());
		refResultMap.put("refTotalCnt", refList.getTotalElements());
		refResultMap.put("refTotalPage", refList.getTotalPages());
		
		return refResultMap;
	}
	
	public RefBoardResponseDto findById(Long refId) {
		refBoardRepository.updateRefBoardReadCntInc(refId);
		return new RefBoardResponseDto(refBoardRepository.findById(refId).get());
	}
	
	public int updateRefBoard(RefBoardRequestDto refBoardRequestDto) {
		return refBoardRepository.updateRefBoard(refBoardRequestDto);
	}
	
	public void deleteById(Long refId) {
		refBoardRepository.deleteById(refId);
	}
	
	public void deleteAll(Long[] deleteRefId) {
		refBoardRepository.deleteRefBoard(deleteRefId);
	}
}
