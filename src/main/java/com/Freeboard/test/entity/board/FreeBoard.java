package com.Freeboard.test.entity.board;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
//import javax.persistence.Table;
import javax.persistence.OneToMany;

import com.Freeboard.test.entity.BaseTimeEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
//@Table(name = "FreeBoard")
public class FreeBoard extends BaseTimeEntity {

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
