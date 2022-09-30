package com.youth.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


import com.youth.entity.FreeBoard;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
//@Table(name = "FreeBoard")
public class FreeBoard extends FreeBoardTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long fboardno;
	private String title;
	private String content;
	private int readCnt;
	private String writer;
	

	@Builder
	public FreeBoard(Long fboardno, String title, String content, int readCnt, String writer) {
		this.fboardno = fboardno;
		this.title = title;
		this.content = content;
		this.readCnt = readCnt;
		this.writer = writer;
	}


}
