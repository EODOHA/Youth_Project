package com.youth.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.youth.dto.IntroFormDto;
import com.youth.dto.IntroImgDto;
import com.youth.dto.IntroSearchDto;
import com.youth.dto.MainIntroDto;
import com.youth.entity.Intro;
import com.youth.entity.IntroImg;
import com.youth.repository.IntroImgRepository;
import com.youth.repository.IntroRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class IntroService {

	private final IntroRepository introRepository;
	private final IntroImgService introImgService;
	private final IntroImgRepository introImgRepository;
	
	public Long saveIntro(IntroFormDto introFormDto, List<MultipartFile> introImgFileList) throws Exception {
		// 인사말 등록
		Intro intro = introFormDto.createIntro();
		introRepository.save(intro);
		
		// 이미지 등록
		for(int i = 0; i < introImgFileList.size(); i++) {
			IntroImg introImg = new IntroImg();
			introImg.setIntro(intro);
			
			if (i == 0)
				introImg.setRepimgYn("Y");
			else
				introImg.setRepimgYn("N");
			introImgService.saveIntroImg(introImg, introImgFileList.get(i));
		}
		
		return intro.getId();
	}
	
	@Transactional(readOnly = true)
	public IntroFormDto getIntroDtl(Long introId) {
		List<IntroImg> introImgList = introImgRepository.findByIntroIdOrderByIdAsc(introId);
		List<IntroImgDto> introImgDtoList = new ArrayList<>();
		for (IntroImg introImg : introImgList) {
			IntroImgDto introImgDto = IntroImgDto.of(introImg);
			introImgDtoList.add(introImgDto);
		}
		
		Intro intro = introRepository.findById(introId)
				.orElseThrow(EntityNotFoundException::new);
		IntroFormDto introFormDto = IntroFormDto.of(intro);
		introFormDto.setIntroImgDtoList(introImgDtoList);
		return introFormDto;
	}
	
	public Long updateIntro(IntroFormDto introFormDto, List<MultipartFile> introImgFileList) throws Exception {
		// 설명글 수정
		Intro intro = introRepository.findById(introFormDto.getId())
				.orElseThrow(EntityNotFoundException::new); // 설명글화면으로 전달 받은 설명 아이디를 이용 설명 엔티티 조회 
		intro.updateIntro(introFormDto); // 설명등록화면으로 전달 받은 IntroFormDto를 통해 설명 엔티티 업데이트
		List<Long> introImgIds = introFormDto.getIntroImgIds(); // 설명 이미지 아이디 리스트 조회
		// 이미지 등록
		for (int i = 0; i < introImgFileList.size(); i++) {
			introImgService.updateIntroImg(introImgIds.get(i), introImgFileList.get(i));
		}
		return intro.getId();
	}
	
	@Transactional(readOnly = true)
	public Page<Intro> getAdminIntroPage(IntroSearchDto introSearchDto, Pageable pageable) {
		return introRepository.getAdminIntroPage(introSearchDto, pageable);
	}
	
	@Transactional(readOnly = true)
	public Page<MainIntroDto> getMainIntroPage(IntroSearchDto introSearchDto, Pageable pageable) {
		return introRepository.getMainIntroPage(introSearchDto, pageable);
	}
}
