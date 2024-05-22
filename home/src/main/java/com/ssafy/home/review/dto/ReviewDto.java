package com.ssafy.home.review.dto;

import lombok.Data;

@Data
public class ReviewDto {
	private int id;
	private String content;
	private String nickname;
	private String houseManageNo;
	private int isMyReview;
}
