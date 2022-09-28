package com.youth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.youth.entity.Intro;

public interface IntroRepository extends JpaRepository<Intro, Long>, QuerydslPredicateExecutor<Intro>, IntroRepositoryCustom {
	
	List<Intro> findByIntroTitle(String introTitle);
	List<Intro> findByIntroTitleOrIntroDetail(String introTitle, String introDetail);
}
