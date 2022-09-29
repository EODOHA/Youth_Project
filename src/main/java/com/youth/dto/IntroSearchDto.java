package com.youth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IntroSearchDto {

	private String searchDateType;
	private String searchBy;
	private String searchQuery ="";
}
