package com.ssafy.home.config.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class JwtToken {
	//인증 타입으로 bearer인증 타입
	private String grantType;
	
	//실제 토큰
	private String accessToken;
	
	//만료 되는 경우
	private String refreshToken;
}
