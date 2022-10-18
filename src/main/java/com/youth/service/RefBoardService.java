package com.youth.service;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.youth.dto.RefBoardRequestDto;
import com.youth.dto.RefBoardResponseDto;
import com.youth.entity.RefBoard;
import com.youth.repository.RefBoardRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class RefBoardService {

	private final RefBoardRepository refBoardRepository;
	private final RefBoardFileService refBoardFileService;
	
	@Transactional
	public boolean save(RefBoardRequestDto refBoardRequestDto, MultipartHttpServletRequest multiRequest) throws Exception {
		
		RefBoard refResult = refBoardRepository.save(refBoardRequestDto.toEntity());
		
		boolean refResultFlag = false;
		
		if (refResult != null) {
			refBoardFileService.uploadFile(multiRequest, refResult.getRefId());
			refResultFlag = true;
		}
		
		return refResultFlag;
	}
	
	@Transactional(readOnly = true)
	public HashMap<String, Object> findAll(Integer page, Integer size) throws Exception {
		
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		
		Page<RefBoard> refList = refBoardRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "refRegisterTime")));
		
		resultMap.put("refList", refList.stream().map(RefBoardResponseDto::new).collect(Collectors.toList()));
		resultMap.put("refPaging", refList.getPageable());
		resultMap.put("refTotalCnt", refList.getTotalElements());
		resultMap.put("refTotalPage", refList.getTotalPages());
		
		return resultMap;
	}
	
	public HashMap<String, Object> findById(Long refId) throws Exception {
		
		HashMap<String, Object> refResultMap = new HashMap<String, Object>(); 
		
		refBoardRepository.updateRefBoardReadCntInc(refId);
		
		RefBoardResponseDto refInfo = new RefBoardResponseDto(refBoardRepository.findById(refId).get());
		
		refResultMap.put("refInfo", refInfo);
		refResultMap.put("fileList", refBoardFileService.findByRefBoardId(refInfo.getRefId()));
		
		return refResultMap;
	}
	
	public boolean updateRefBoard(RefBoardRequestDto boardRequestDto, MultipartHttpServletRequest multiRequest) throws Exception {
		
		int refResult = refBoardRepository.updateRefBoard(boardRequestDto);
		
		boolean resultFlag = false;
		
		if (refResult > 0) {
			refBoardFileService.uploadFile(multiRequest, boardRequestDto.getRefId());
			resultFlag = true;
		}
		
		return resultFlag;
	}
	
	public void deleteById(Long refId) throws Exception {
		Long[] refIdArr = {refId};
		refBoardFileService.deleteRefBoardFileYn(refIdArr);
		refBoardRepository.deleteById(refId);
	}
	
	public void deleteAll(Long[] deleteRefIdList) throws Exception {
		refBoardFileService.deleteRefBoardFileYn(deleteRefIdList);
		refBoardRepository.deleteRefBoard(deleteRefIdList);
	}
}
