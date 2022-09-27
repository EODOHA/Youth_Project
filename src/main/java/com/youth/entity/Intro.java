package com.youth.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.youth.dto.IntroFormDto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="intro")
@Getter
@Setter
@ToString
public class Intro extends BaseEntity {
	
	@Id
	@Column(name = "intro_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	private String introTitle;	// 제목
	
	@Column(name = "introCont", nullable = false)
	private String introCont;	// 내용
	
	@Lob
	@Column(name = "introDetail", nullable = false)
	private String introDetail;
	
	@OneToOne
	@JoinColumn(name = "member_id")
	private Member member;
	
	public void updateIntro(IntroFormDto introFormDto) {
		this.introTitle = introFormDto.getIntroTitle();
		this.introCont = introFormDto.getIntroCont();
		this.introDetail = introFormDto.getIntroDetail();
	}
	
	
}
