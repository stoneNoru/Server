package com.ssafy.home.notice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.home.notice.dto.NoticeDto;
import com.ssafy.home.notice.model.service.NoticeService;
import com.ssafy.home.util.ResultDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/notices")
@CrossOrigin("*")
@RequiredArgsConstructor
@Slf4j
public class NoticeController {
	private final NoticeService noticeService;
	
	
	@GetMapping
	public ResponseEntity<?> selectAll() {
		List<NoticeDto> list = noticeService.selectAll();
		
		return ResponseEntity.ok(ResultDto.res(HttpStatus.OK.value(), "공지 사항입니다.",list));
	}
	
}
