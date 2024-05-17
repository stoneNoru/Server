package com.ssafy.home.lotto.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Lotto {
	private String houseManageNo;

	private String pblancNo;

	//주택명 및 주소
	private String houseNm;

	private String hssplyAdres;

	//공급 주체
	private String bsnsMbyNm;

	//주택 유형
	private String houseSecdNm;

	//공급 세대 수
	private int totSuplyHshldco;

	//청약 일정
	private String rceptBgnde;

	private String rceptEndde;

	private String przwnerPresnatnDe;

	//계약일
	private String cntrctCnclsBgnde;

	private String cntrctCnclsEndde;

	//입주일
	private String mvnPrearngeYm;

	//문의처
	private String mdhsTelno;

	private String hmpgAdres;

	private String subscrptAreaCodeNm;

	private String pblancUrl;

	
}
