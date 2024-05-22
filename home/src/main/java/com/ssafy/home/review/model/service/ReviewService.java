package com.ssafy.home.review.model.service;

import java.util.List;

import com.ssafy.home.review.dto.ReviewDto;

public interface ReviewService {

	List<ReviewDto> findByApplyId(String houseManageNo, String userId);

	int regist(ReviewDto review);

	int deleteById(int id, String nickname);

}
