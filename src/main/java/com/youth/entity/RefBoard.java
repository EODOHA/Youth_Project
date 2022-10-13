package com.youth.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class RefBoard extends RefBaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long refId;
	private String refTitle;
	private String refContent;
	private int refReadCnt;
	private String refRegisterId;
	
	@Builder
	public RefBoard(Long refId, String refTitle, String refContent, int refReadCnt, String refRegisterId) {
		this.refId = refId;
		this.refTitle = refTitle;
		this.refContent = refContent;
		this.refReadCnt = refReadCnt;
		this.refRegisterId = refRegisterId;
	}
}
