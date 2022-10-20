package com.youth.dto;

import com.youth.entity.RefBoard;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RefBoardRequestDto {

	private Long refId;
	private String refTitle;
	private String refContent;
	private String refRegisterId;
	
	public RefBoard toEntity() {
		return RefBoard.builder()
			.refTitle(refTitle)
			.refContent(refContent)
			.refRegisterId(refRegisterId)
			.build();
	}
	
	@Override
	public String toString() {
		return "RefBoardRequestDto [refId=" + refId + ", refTitle=" + refTitle + ", refContent=" + refContent + ", refRegisterId=" + refRegisterId
				+ "]";
	}
}
