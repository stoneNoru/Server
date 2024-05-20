package com.ssafy.home.apt.model.service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssafy.home.apt.dto.AptBookmarkDto;
import com.ssafy.home.apt.dto.DealDto;
import com.ssafy.home.apt.dto.PositionDto;
import com.ssafy.home.apt.dto.ResultBookmarkDto;
import com.ssafy.home.apt.dto.SearchDto;
import com.ssafy.home.apt.model.mapper.HomeMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HomeServiceImpl implements HomeService{
    private final HomeMapper homeMapper;

    @Override
    public List<SearchDto> selectByKeyword(String keyword) {
        return homeMapper.selectByKeyword(keyword);
    }

	@Override
	public List<DealDto> selectByPosition(PositionDto position) {
		List<DealDto> dealDto = homeMapper.selectByPosition(position);
		List<DealDto> list = new ArrayList<>();
		for (DealDto aptCode : dealDto) {
		    Map<String, Object> paramMap = new HashMap<>();
		    paramMap.put("aptCode", aptCode.getAptCode());
		    DealDto deal = homeMapper.selectByNew(paramMap);
		    list.add(deal);
		}
		return list;
	}

	@Override
	public List<DealDto> selectByCode(String aptCode) {
		return homeMapper.selectByCode(aptCode, 10);
	}


	@Override
	public int registBookmark(AptBookmarkDto bookmark) {
		return homeMapper.registBookmark(bookmark);
	}

	@Override
	public List<ResultBookmarkDto> findBookmarkDetailsByUserId(String id) {
		return homeMapper.findBookmarkDetailsByUserId(id);
	}

	@Override
	public int deleteBookmark(String userId, String aptCode) {
		return homeMapper.deleteBookmark(userId, aptCode);
	}
    
	
    
}
