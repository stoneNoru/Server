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
	//주택관리번호
	@JsonProperty("HOUSE_MANAGE_NO")
	private String house_MANAGE_NO;
	
	@JsonProperty("PBLANC_NO")
	private String pblanc_NO;
	
	//주택명 및 주소
	@JsonProperty("HOUSE_NM")
	private String house_NM;
	
	@JsonProperty("HSSPLY_ADRES")
	private String hssply_ADRES;
	
	//공급 주체
	@JsonProperty("BSNS_MBY_NM")
	private String bsns_MBY_NM;
	
	//주택 유형
	@JsonProperty("HOUSE_SECD_NM")
	private String house_SECD_NM;
	//공급 세대 수
	@JsonProperty("TOT_SUPLY_HSHLDCO")
	private int tot_SUPLY_HSHLDCO;
	
	//청약 일정
	@JsonProperty("RCEPT_BGNDE")
	private String rcept_BGNDE;

	@JsonProperty("RCEPT_ENDDE")
	private String rcept_ENDDE;
	
	@JsonProperty("PRZWNER_PRESNATN_DE")
	private String przwner_PRESNATN_DE;
	
	//계약일
	@JsonProperty("CNTRCT_CNCLS_BGNDE")
	private String cntrct_CNCLS_BGNDE;
	
	@JsonProperty("CNTRCT_CNCLS_ENDDE")
	private String cntrct_CNCLS_ENDDE;
	
	//입주일
	@JsonProperty("MVN_PREARNGE_YM")
	private String mvn_PREARNGE_YM;
	
	//문의처
	@JsonProperty("MDHS_TELNO")
	private String mdhs_TELNO;
	
	@JsonProperty("HMPG_ADRES")
	private String hmpg_ADRES;
	
	@JsonProperty("SUBSCRPT_AREA_CODE_NM")
	private String subscrpt_AREA_CODE_NM;
	
	@JsonProperty("PBLANC_URL")
	private String pblanc_URL;
	
}
