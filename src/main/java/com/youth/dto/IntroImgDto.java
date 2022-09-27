package com.youth.dto;

import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import com.youth.entity.IntroImg;

@Getter
@Setter
public class IntroImgDto {

	private Long id;
	private String imgName;
	private String oriImgName;
	private String imgUrl;
	private String repImgYn;
	private static ModelMapper modelMapper = new ModelMapper();
	
	public static IntroImgDto of(IntroImg introImg) {
		return modelMapper.map(introImg, IntroImgDto.class);
	}
}
