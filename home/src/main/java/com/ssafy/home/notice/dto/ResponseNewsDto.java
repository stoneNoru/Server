package com.ssafy.home.notice.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class ResponseNewsDto {
	private String title;
	private String link;
	private String description;
	private String pubDate;
}