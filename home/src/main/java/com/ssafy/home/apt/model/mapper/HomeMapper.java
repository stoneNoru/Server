package com.ssafy.home.apt.model.mapper;

import java.util.List;
import java.util.Map;

import com.ssafy.home.apt.dto.AptBookmarkDto;
import com.ssafy.home.apt.dto.DealDto;
import com.ssafy.home.apt.dto.PositionDto;
import com.ssafy.home.apt.dto.ResultAptBookmarkDto;
import com.ssafy.home.apt.dto.SearchDto;

public interface HomeMapper {
    List<SearchDto> selectByKeyword(String keyword);
    
	List<DealDto> selectByPosition(PositionDto position);
	
	DealDto selectByNew(Map<String, Object> paramMap);
	
	List<DealDto> selectByCode(String aptCode, int limit);
	
	
	int registBookmark(AptBookmarkDto bookmark);
	
	List<ResultAptBookmarkDto> findBookmarkDetailsByUserId(String id);
	
	int deleteBookmark(String userId, String aptCode);
}

