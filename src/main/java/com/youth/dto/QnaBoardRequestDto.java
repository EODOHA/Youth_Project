package com.youth.dto;

import com.youth.entity.QnaBoard;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class QnaBoardRequestDto {
	private Long qboardno;
	private String title;
	private String content;
	private String writer;
	
	public QnaBoard toEntity() {
		return QnaBoard.builder()
				.title(title)
				.content(content)
				.writer(writer)
				.build();
	}

}