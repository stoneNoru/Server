package com.ssafy.home.apt.model.service;

import java.util.List;

import com.ssafy.home.apt.dto.DealDto;
import com.ssafy.home.apt.dto.PositionDto;
import com.ssafy.home.apt.dto.SearchDto;

public interface HomeService {

    List<SearchDto> selectByKeyword(String keyword);

	List<DealDto> selectByPosition(PositionDto position);

	List<DealDto> selectByCode(String aptCode);
}
