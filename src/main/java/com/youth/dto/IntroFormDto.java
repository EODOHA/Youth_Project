package com.youth.dto;

import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import com.youth.entity.Intro;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class IntroFormDto {
	
	private Long id;
	
	@NotBlank(message = "제목은 필수 입력 값입니다.")
	private String introTitle;
	
	@NotBlank(message = "내용은 필수 입력 값입니다.")
	private String introCont;
	
	@NotBlank(message = "상세 내용은 필수 입력 값입니다.")
	private String introDetail;
	
	private List<IntroImgDto> introImgDtoList = new ArrayList<>();
	
	private List<Long> introImgIds = new ArrayList<>();
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	public Intro createIntro() {
		return modelMapper.map(this, Intro.class);
	}
	
	public static IntroFormDto of(Intro intro) {
		return modelMapper.map(intro, IntroFormDto.class);
	}

}
