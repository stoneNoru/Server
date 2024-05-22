package com.ssafy.home.review.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.home.review.dto.ReviewDto;
import com.ssafy.home.review.model.mapper.ReviewMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
	
	private final ReviewMapper reviewMapper;
	

	@Override
	public List<ReviewDto> findByApplyId(String houseManageNo, String userId) {
		return reviewMapper.findByApplyId(houseManageNo, userId);
	}


	@Override
	public int regist(ReviewDto review) {
		return reviewMapper.regist(review);
	}


	@Override
	public int deleteById(int id, String nickname) {
		return reviewMapper.deleteById(id, nickname);
	}

	
	
}
