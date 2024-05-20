package com.ssafy.home.notice.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.home.notice.dto.NoticeDto;
import com.ssafy.home.notice.model.mapper.NoticeMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {
	
	private final NoticeMapper noticeMapper;

	@Override
	public List<NoticeDto> selectAll() {
		return noticeMapper.selectAll();
	}

}
