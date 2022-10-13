package com.youth.dto;

import java.time.LocalDateTime;

import com.youth.entity.RefBoard;

import lombok.Getter;

@Getter
public class RefBoardResponseDto {

	private Long refId;
	private String refTitle;
	private String refContent;
	private int refReadCnt;
	private String refRegisterId;
	private LocalDateTime refRegisterTime;
	
	public RefBoardResponseDto(RefBoard entity) {
		this.refId = entity.getRefId();
		this.refTitle = entity.getRefTitle();
		this.refContent = entity.getRefContent();
		this.refRegisterId = entity.getRefRegisterId();
		this.refRegisterTime = entity.getRefRegisterTime();
	}
	
	@Override
	public String toString() {
		return "RefBoardListDto [refId=" + refId + ", refTitle=" + refTitle + ", refContent" + refContent +
				", refReadCnt=" + refReadCnt + ", refRegisterId=" + refRegisterId + ", refRegisterTime=" + refRegisterTime + "]";
	}
}
