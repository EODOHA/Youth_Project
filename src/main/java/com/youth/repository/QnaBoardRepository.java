package com.youth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.youth.dto.QnaBoardRequestDto;
import com.youth.entity.QnaBoard;

public interface QnaBoardRepository extends JpaRepository<QnaBoard, Long>{
	
	String UPDATE_QNABOARD = "UPDATE qna_board " +
			"SET TITLE = :#{#QnaBoardRequestDto.title}, " +
			"CONTENT = :#{#QnaBoardRequestDto.content}, " +
			"update_date = NOW() " +
			"WHERE qboardno = :#{#QnaBoardRequestDto.qboardno}";
	
	@Transactional
	@Modifying
	@Query(value = UPDATE_QNABOARD, nativeQuery = true)
	public int updateQnaBoard(@Param("QnaBoardRequestDto") QnaBoardRequestDto qbRequestDto);
	
	static final String UPDATE_QNABOARD_READ_CNT_INC = "UPDATE qna_board "
										+ "SET read_cnt = read_cnt + 1 "
										+ "WHERE qboardno = :qboardno";
	
	static final String DELETE_QNABOARD = "DELETE FROM qna_board "
									+ "WHERE qboardno IN (:deleteList)";
	
	@Transactional
	@Modifying
	@Query(value = UPDATE_QNABOARD_READ_CNT_INC, nativeQuery = true)
	public int updateQnaBoardReadCntInc(@Param("qboardno")Long qboardno);
	
	@Transactional
	@Modifying
	@Query(value = DELETE_QNABOARD, nativeQuery = true)
	public int deleteQnaBoard(@Param("deleteList") Long[] deleteList);

}