package com.youth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.youth.entity.IntroImg;

public interface IntroImgRepository extends JpaRepository<IntroImg, Long> {

	List<IntroImg> findByIntroIdOrderByIdAsc(Long introId);
}
