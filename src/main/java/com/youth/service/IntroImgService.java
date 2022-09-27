package com.youth.service;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import com.youth.entity.IntroImg;
import com.youth.repository.IntroImgRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class IntroImgService {

	@Value("${introImgLocation}")
	private String introImgLocation;
	
	private final IntroImgRepository introImgRepository;
	private final FileService fileService;
	
	public void saveIntroImg(IntroImg introImg, MultipartFile introImgFile) throws Exception {
		String oriImgName = introImgFile.getOriginalFilename();
		String imgName = "";
		String imgUrl = "";
		
		// 파일 업로드
		if(!StringUtils.isEmpty(oriImgName)) {
			imgName = fileService.uploadFile(introImgLocation, oriImgName, introImgFile.getBytes());
			imgUrl = "/images/intro/" + imgName;
		}
		
		// 상품 이미지 정보 저장
		introImg.updateIntroImg(oriImgName, imgName, imgUrl);
		introImgRepository.save(introImg);
	}
	
	public void updateIntroImg(Long introImgId, MultipartFile introImgFile) throws Exception {
		if(!introImgFile.isEmpty()) {
			IntroImg savedIntroImg = introImgRepository.findById(introImgId)
					.orElseThrow(EntityNotFoundException::new);
			
			// 기존 이미지 파일 삭제
			if(!StringUtils.isEmpty(savedIntroImg.getImgName())) {
				fileService.deleteFile(introImgLocation+"/"+savedIntroImg.getImgName());
			}
			
			String oriImgName = introImgFile.getOriginalFilename();
			String imgName = fileService.uploadFile(introImgLocation,  oriImgName,  introImgFile.getBytes());
			String imgUrl = "/images/intro/" + imgName;
			savedIntroImg.updateIntroImg(oriImgName, imgName, imgUrl);
		}
	}
}
