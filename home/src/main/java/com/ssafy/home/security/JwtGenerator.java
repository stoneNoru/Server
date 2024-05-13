package com.ssafy.home.security;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ssafy.home.config.auth.dto.JwtToken;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtGenerator {
	private final Key key;
	private static final String BEARER_TYPE = "Bearer";

	//생성자 autowire
	public JwtGenerator(@Value("${jwt.secret}") String secretKey) {
		
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		
		this.key = Keys.hmacShaKeyFor(keyBytes);
	}
	
	//사용자 id로 토큰 생성하기
	public JwtToken generateToken(String id, String auth) {
		
		//토큰 만료 시간 3시간
		Date expireTime = new Date();
		expireTime.setTime(expireTime.getTime()+1000*60*3);
		
		//access토큰
		String accessToken = Jwts.builder()
				.setSubject(String.valueOf(id))
				.setExpiration(expireTime)
				.claim("auth", auth)
				.signWith(key, SignatureAlgorithm.HS256)
				.compact();
		
		//refresh토큰
		String refreshToken = Jwts.builder()
				.setExpiration(expireTime)
				.signWith(key, SignatureAlgorithm.HS256)
				.compact();
		
		return JwtToken.builder()
				//BEARER_TYPE 토큰 형식
				.grantType(BEARER_TYPE)
				.accessToken(accessToken)
				.refreshToken(refreshToken)
				.build();
	}
	
	
}
