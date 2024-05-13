package com.ssafy.autosave.lotto.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class Lotto {
    //주택관리번호
    @JsonProperty("HOUSE_MANAGE_NO")
    private String houseManageNo;

    @JsonProperty("PBLANC_NO")
    private String pblancNo;

    //주택명 및 주소
    @JsonProperty("HOUSE_NM")
    private String houseNo;

    @JsonProperty("HSSPLY_ADRES")
    private String hssplyAdres;

    //공급 주체
    @JsonProperty("BSNS_MBY_NM")
    private String bsnsMbyNm;

    //주택 유형
    @JsonProperty("HOUSE_SECD_NM")
    private String houseSecdNm;
    //공급 세대 수
    @JsonProperty("TOT_SUPLY_HSHLDCO")
    private int totSuplyHshldco;

    //청약 일정
    @JsonProperty("RCEPT_BGNDE")
    private String rceptBgnde;

    @JsonProperty("RCEPT_ENDDE")
    private String rceptEndde;

    @JsonProperty("PRZWNER_PRESNATN_DE")
    private String przwnerPresnatnDe;

    //계약일
    @JsonProperty("CNTRCT_CNCLS_BGNDE")
    private String cntrctCnclsBgnde;

    @JsonProperty("CNTRCT_CNCLS_ENDDE")
    private String cntrctCnclsEndde;

    //입주일
    @JsonProperty("MVN_PREARNGE_YM")
    private String mvnPrearngeYm;

    //문의처
    @JsonProperty("MDHS_TELNO")
    private String mdhsTelno;

    @JsonProperty("HMPG_ADRES")
    private String hmpgAdres;

    @JsonProperty("SUBSCRPT_AREA_CODE_NM")
    private String subscrptAreaCodeNm;

    @JsonProperty("PBLANC_URL")
    private String pblancUrl;

}
