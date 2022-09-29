package com.youth.repository;

import com.youth.dto.IntroSearchDto;
import com.youth.dto.MainIntroDto;
import com.youth.entity.Intro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IntroRepositoryCustom {

	Page<Intro> getAdminIntroPage(IntroSearchDto introSearchDto, Pageable pageable);
	
	Page<MainIntroDto> getMainIntroPage(IntroSearchDto introSearchDto, Pageable pageable);
}
