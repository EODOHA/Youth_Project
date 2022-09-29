package com.youth.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.youth.dto.IntroSearchDto;
import com.youth.dto.MainIntroDto;
import com.youth.dto.QMainIntroDto;
import com.youth.entity.Intro;
import com.youth.entity.QIntro;
import com.youth.entity.QIntroImg;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;
import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

public class IntroRepositoryCustomImpl implements IntroRepositoryCustom {

	private JPAQueryFactory queryFactory;
	
	public IntroRepositoryCustomImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}
	
	private BooleanExpression regDtsAfter(String searchDateType) {
		LocalDateTime dateTime = LocalDateTime.now();
		
		if(StringUtils.equals("all", searchDateType) || searchDateType == null) {
			return null;
		} else if(StringUtils.equals("1d", searchDateType)) {
			dateTime = dateTime.minusDays(1);
		} else if(StringUtils.equals("1w", searchDateType)) {
			dateTime = dateTime.minusWeeks(1);
		} else if(StringUtils.equals("1m", searchDateType)) {
			dateTime = dateTime.minusMonths(1);
		}
		return QIntro.intro.regTime.after(dateTime);
	}
	
	private BooleanExpression searchByLike(String searchBy, String searchQuery) {
		
		if(StringUtils.equals("introTitle", searchBy)) {
			return QIntro.intro.introTitle.like("%" + searchQuery + "%");
		} else if(StringUtils.equals("createdBy", searchBy)) {
			return QIntro.intro.createdBy.like("%" + searchQuery + "%");
		}
		return null;
	}
	
	@Override
	public Page<Intro> getAdminIntroPage(IntroSearchDto introSearchDto, Pageable pageable) {
		
		QueryResults<Intro> results = queryFactory
				.selectFrom(QIntro.intro)
				.where(regDtsAfter(introSearchDto.getSearchDateType()),
						searchByLike(introSearchDto.getSearchBy(),
								introSearchDto.getSearchQuery()))
				.orderBy(QIntro.intro.id.desc())
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.fetchResults();
		
		List<Intro> content = results.getResults();
		long total = results.getTotal();
		
		return new PageImpl<>(content, pageable, total);
	}
	
	private BooleanExpression introTitleLike(String searchQuery) {
		return StringUtils.isEmpty(searchQuery) ? null : QIntro.intro.introTitle.like("%" + searchQuery + "%");
	}
	
	@Override
	public Page<MainIntroDto> getMainIntroPage(IntroSearchDto introSearchDto, Pageable pageable) {
		QIntro intro = QIntro.intro;
		QIntroImg introImg = QIntroImg.introImg;
		
		QueryResults<MainIntroDto> results = queryFactory
				.select(
					new QMainIntroDto(
							intro.id,
							intro.introTitle,
							intro.introDetail,
							introImg.imgUrl)
				)
				.from(introImg)
				.join(introImg.intro, intro)
				.where(introImg.repimgYn.eq("Y"))
				.where(introTitleLike(introSearchDto.getSearchQuery()))
				.orderBy(intro.id.desc())
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.fetchResults();
		List<MainIntroDto> content = results.getResults();
		long total = results.getTotal();
		return new PageImpl<>(content, pageable, total);
	}

}
