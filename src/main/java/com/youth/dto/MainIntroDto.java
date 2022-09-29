package com.youth.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MainIntroDto {

	private Long id;
	private String introTitle;
	private String introDetail;
	private String imgUrl;
	
	@QueryProjection
	public MainIntroDto(Long id, String introTitle, String introDetail, String imgUrl) {
		this.id = id;
		this.introTitle = introTitle;
		this.introDetail = introDetail;
		this.imgUrl = imgUrl;
	}
}
