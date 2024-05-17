package com.ssafy.home.apt.dto;

import lombok.Data;

@Data
public class DealDto {
	private long no;
	private String dongCode;
	private String dealAmount;
	private int dealYear;
	private int dealMonth;
	private int dealDay;
	private String floor;
	private String area;
	private String apartmentName;
	private String aptCode;
	private String lng;
	private String lat;
	private String date;
	
}
