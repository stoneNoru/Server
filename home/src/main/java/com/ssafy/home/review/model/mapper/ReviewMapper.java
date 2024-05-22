package com.ssafy.home.review.model.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ssafy.home.review.dto.ReviewDto;

@Repository
public interface ReviewMapper {
	List<ReviewDto> findByApplyId(String houseManageNo, String userId);
	
	int regist(ReviewDto review);
	
	int deleteById(int id, String nickname);
}
