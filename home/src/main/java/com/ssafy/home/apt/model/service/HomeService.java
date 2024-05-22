package com.ssafy.home.apt.model.service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import com.ssafy.home.apt.dto.AptBookmarkDto;
import com.ssafy.home.apt.dto.DealDto;
import com.ssafy.home.apt.dto.PositionDto;
import com.ssafy.home.apt.dto.ResultBookmarkDto;
import com.ssafy.home.apt.dto.SearchDto;

public interface HomeService {

    List<SearchDto> selectByKeyword(String keyword);

	List<DealDto> selectByPosition(PositionDto position);

	List<DealDto> selectByCode(String aptCode);

	int registBookmark(AptBookmarkDto bookmark);
	
	List<DealDto> findBookmarkDetailsByUserId(String id);

	int deleteBookmark(String userId, String aptCode);

	int findBookmarkByIdAndAptCode(String aptCode, String userId);
}
