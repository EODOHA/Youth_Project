package com.youth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.youth.entity.Intro;

public interface LectureRepository extends JpaRepository<Intro, Long>, QuerydslPredicateExecutor<Intro>, IntroRepositoryCustom {
	
	List<Intro> findByIntroTitle(String lectureNm);
	List<Intro> findByIntroTitleOrIntroDetail(String lectureNm, String lectureDetail);
}
