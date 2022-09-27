package com.youth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.youth.entity.Intro;

public interface IntroRepository extends JpaRepository<Intro, Long> {
	
	List<Intro> findByIntroTitle(String introTitle);
	List<Intro> findByIntroTitleOrIntroDetail(String introTitle, String introDetail);
}
