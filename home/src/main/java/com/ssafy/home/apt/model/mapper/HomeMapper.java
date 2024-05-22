package com.ssafy.home.apt.model.mapper;

import java.util.List;
import java.util.Map;

import com.ssafy.home.apt.dto.AptBookmarkDto;
import com.ssafy.home.apt.dto.DealDto;
import com.ssafy.home.apt.dto.PositionDto;
import com.ssafy.home.apt.dto.ResultBookmarkDto;
import com.ssafy.home.apt.dto.SearchDto;

public interface HomeMapper {
    List<SearchDto> selectByKeyword(String keyword);
    
	List<DealDto> selectByPosition(PositionDto position);
	
	DealDto selectByNew(Map<String, Object> paramMap);
	
	List<DealDto> selectByCode(String aptCode, int limit);
	
	int findBookmarkByIdAndAptCode(String aptCode, String userId);
	
	int registBookmark(AptBookmarkDto bookmark);
	
	List<ResultBookmarkDto> findBookmarkDetailsByUserId(String userId);
	
	int deleteBookmark(String userId, String aptCode);
	
	List<String> selectByUserId(String userId);
}

