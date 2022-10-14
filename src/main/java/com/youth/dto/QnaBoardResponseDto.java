package com.youth.dto;

import java.time.LocalDateTime;

import com.youth.entity.QnaBoard;

import lombok.Getter;

@Getter
public class QnaBoardResponseDto {
	private Long qboardno;
	private String title;
	private String content;
	private int readCnt;
	private String writer;
	private LocalDateTime createDate;
	
	
	public QnaBoardResponseDto(QnaBoard entity) {
		this.qboardno = entity.getQboardno();
		this.title = entity.getTitle();
		this.content = entity.getContent();
		this.readCnt = entity.getReadCnt();
		this.writer = entity.getWriter();
		this.createDate = entity.getCreateDate();
		
	}
	
	@Override
	public String toString() {
		return "QnaBoardResponseDto [qboardno=" + qboardno + ", title=" + title +
				", content=" + content + ", readCnt=" + readCnt +
				", writer=" + writer + ", createDate=" + createDate + "]";
	}

}