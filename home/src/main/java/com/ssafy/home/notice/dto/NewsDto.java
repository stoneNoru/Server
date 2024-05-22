package com.ssafy.home.notice.dto;

import lombok.Data;

@Data
public class NewsDto {
	private String lastBuildDate;
	private int total;
	private int start;
	private int display;
	private Item[] items;
}
