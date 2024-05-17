package com.ssafy.home.exception;

import java.sql.SQLException;

import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ssafy.home.util.ResultDto;

@RestControllerAdvice
public class MyAdvice {
	@ExceptionHandler(SQLException.class)
	public ResponseEntity<?> sqlException() {
		return ResponseEntity.status(HttpStatus.CONFLICT).body(ResultDto.res(HttpStatus.CONFLICT.value(), "이미 존재하는 데이터입니다."));
	}
	
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<?> notFoundException() {
		return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(new ResultDto(HttpStatus.NOT_FOUND.value(), "정보를 찾을 수 없습니다."));		
	}
	
	@ExceptionHandler(UnAuthorizedException.class)
	public ResponseEntity<?> unAuthorizedException() {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value()).body(new ResultDto(HttpStatus.UNAUTHORIZED.value(), "인증에 실패했습니다."));
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<?> userNotFoundException() {
		return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(new ResultDto(HttpStatus.NOT_FOUND.value(), "유저 정보를 찾을 수 없습니다."));
	}
}
