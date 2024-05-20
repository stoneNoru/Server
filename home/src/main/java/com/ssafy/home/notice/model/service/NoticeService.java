package com.ssafy.home.notice.model.service;

import java.util.List;

import com.ssafy.home.notice.dto.NoticeDto;

public interface NoticeService {

	List<NoticeDto> selectAll();

}
