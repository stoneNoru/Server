package com.ssafy.home.review.controller;

import java.util.List;

import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.home.exception.UnAuthorizedException;
import com.ssafy.home.exception.UserNotFoundException;
import com.ssafy.home.review.dto.ReviewDto;
import com.ssafy.home.review.model.service.ReviewService;
import com.ssafy.home.user.dto.User;
import com.ssafy.home.user.model.service.UserService;
import com.ssafy.home.util.AuthorizationUtils;
import com.ssafy.home.util.JWTUtil;
import com.ssafy.home.util.ResultDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
@CrossOrigin("*")
@Slf4j
public class ReviewController {

	private final ReviewService reviewService;
	private final UserService userService;
	private final JWTUtil jwtUtil;
    private final AuthorizationUtils authorizationUtils;

	@GetMapping("/{apply-id}")
	public ResponseEntity<?> findByApplyId(@PathVariable("apply-id") String id) {

		List<ReviewDto> list = reviewService.findByApplyId(id);		

		return ResponseEntity.status(HttpStatus.OK.value()).body(ResultDto.res(HttpStatus.OK.value(), "조회에 성공했습니다.", list));
	}

	@PostMapping
	public ResponseEntity<?> registReview(@RequestHeader("authorization") HttpHeaders tokenHeader, @RequestBody ReviewDto review) throws UserNotFoundException, UnAuthorizedException {
		User userInfo = authorizationUtils.getUserInfoFromToken(tokenHeader);


		review.setNickname(userInfo.getNickname());

		int result = reviewService.regist(review);

		if(result == 0) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(new ResultDto(HttpStatus.BAD_REQUEST.value(), "리뷰 등록에 실패했습니다."));
		}

		return ResponseEntity.status(HttpStatus.OK).body(ResultDto.res(HttpStatus.OK.value(), "리뷰 등록에 성공했습니다."));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteReview(@RequestHeader("authorization") HttpHeaders tokenHeader, @PathVariable("id") int id) throws UserNotFoundException, UnAuthorizedException {
		User userInfo = authorizationUtils.getUserInfoFromToken(tokenHeader);

		int result = reviewService.deleteById(id, userInfo.getNickname());

		if(result == 0) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(new ResultDto(HttpStatus.NOT_FOUND.value(), "리뷰 삭제에 실패했습니다."));
		}

		return ResponseEntity.status(HttpStatus.OK).body(ResultDto.res(HttpStatus.OK.value(), "리뷰 삭제에 성공했습니다."));
	}

}
