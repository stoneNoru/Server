package com.ssafy.home.notice.model.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ssafy.home.notice.dto.NoticeDto;

@Repository
public interface NoticeMapper {
	List<NoticeDto> selectAll();
}
