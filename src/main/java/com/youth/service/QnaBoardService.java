package com.youth.service;

import java.util.HashMap;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.youth.dto.QnaBoardRequestDto;
import com.youth.dto.QnaBoardResponseDto;
import com.youth.entity.QnaBoard;

import com.youth.repository.QnaBoardRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QnaBoardService {
	
	private final QnaBoardRepository qbRepository;
	
	@Transactional
	public Long save(QnaBoardRequestDto qbSaveDto) {
		return qbRepository.save(qbSaveDto.toEntity()).getQboardno();
	}

	/*
	 * 트랜잭션에 readOnly=true 옵션을 주면 스프링 프레임워크가 하이버네이트 세션 플러시 모드를 MANUAL로 설정한다. 이렇게하면
	 * 강제로 플러시를 호출하지 않는 한 플러시가 일어나지 않는다. 따라서 트랜잭션을 커밋하더라도 영속성 컨텍스특 플러시 되지 않아서 엔티티의
	 * 등록, 수정, 삭제가 동작하지 않고, 또한 읽기 전용으로, 영속성 컨텍스트는 변경 감지를 위한 스냅샷을 보관하지 않으므로 성능이 향상된다.
	 */
	
	@Transactional(readOnly = true)
	public HashMap<String, Object> findAll(Integer page, Integer size){
		
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		
		Page<QnaBoard> list = qbRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createDate")));
		
		resultMap.put("list", list.stream().map(QnaBoardResponseDto::new).collect(Collectors.toList()));
		resultMap.put("paging", list.getPageable());
		resultMap.put("totalCnt", list.getTotalElements());
		resultMap.put("totalPage", list.getTotalPages());
		
		return resultMap;
	}
	
	
	public QnaBoardResponseDto findById(Long qboardno) {
		qbRepository.updateQnaBoardReadCntInc(qboardno);
		return new QnaBoardResponseDto(qbRepository.findById(qboardno).get());
	}
	
	public int updateQnaBoard(QnaBoardRequestDto qbRequestDto) {
		return qbRepository.updateQnaBoard(qbRequestDto);
	}
	
	public void deleteById(Long qboardno) {
		qbRepository.deleteById(qboardno);
	}
	
	public void deleteAll(Long[] deleteQboardno) {
		qbRepository.deleteQnaBoard(deleteQboardno);
	}

}