package com.ssafy.home.util;

import java.security.Key;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ssafy.home.exception.UnAuthorizedException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JWTUtil {
	private final Key key;
	private static final String BEARER_TYPE = "Bearer";

	@Value("${jwt.access-token.expiretime}")
	private long accessTokenExpireTime;

	@Value("${jwt.refresh-token.expiretime}")
	private long refreshTokenExpireTime;

	//생성자 autowire
	public JWTUtil(@Value("${jwt.secret}") String secretKey) {
		
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		
		this.key = Keys.hmacShaKeyFor(keyBytes);
	}
	
	public String createAccessToken(String userId) {
		return create(userId, "access-token", accessTokenExpireTime);
	}

//	AccessToken에 비해 유효기간을 길게 설정.
	public String createRefreshToken(String userId) {
		return create(userId, "refresh-token", refreshTokenExpireTime);
	}

//	Token 발급
//		key : Claim에 셋팅될 key 값
//		value : Claim에 셋팅 될 data 값
//		subject : payload에 sub의 value로 들어갈 subject값
//		expire : 토큰 유효기간 설정을 위한 값
//		jwt 토큰의 구성 : header + payload + signature
	private String create(String userId, String subject, long expireTime) {
//		Payload 설정 : 생성일 (IssuedAt), 유효기간 (Expiration), 
//		토큰 제목 (Subject), 데이터 (Claim) 등 정보 세팅.
		Claims claims = Jwts.claims()
				.setSubject(subject) // 토큰 제목 설정 ex) access-token, refresh-token
				.setIssuedAt(new Date()) // 생성일 설정
//				만료일 설정 (유효기간)
				.setExpiration(new Date(System.currentTimeMillis() + expireTime));

//		저장할 data의 key, value
		claims.put("userId", userId);

		String jwt = Jwts.builder()
//			Header 설정 : 토큰의 타입, 해쉬 알고리즘 정보 세팅.
			.setHeaderParam("typ", "JWT").setClaims(claims)
//			Signature 설정 : secret key를 활용한 암호화.
			.signWith(key, SignatureAlgorithm.HS256)
			.compact(); // 직렬화 처리.

		return jwt;
	}

	
//	전달 받은 토큰이 제대로 생성된 것인지 확인 하고 문제가 있다면 UnauthorizedException 발생.
	public boolean checkToken(String token) {
		try {
			token = token.substring(7);
//			Json Web Signature? 서버에서 인증을 근거로 인증 정보를 서버의 private key 서명 한것을 토큰화 한것
//			setSigningKey : JWS 서명 검증을 위한  secret key 세팅
//			parseClaimsJws : 파싱하여 원본 jws 만들기
//			token.substring(0, token.length() - 7);
			Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
//			Claims 는 Map 구현체 형태
			log.debug("claims: {}", claims);
			return true;
		} catch (Exception e) {
			log.error(e.getMessage());
			return false;
		}
	}
	
	public String getUserId(String authorization) {
        Jws<Claims> claims = null;
//        authorization.substring(0, authorization.length() - 7);
        authorization = authorization.substring(7);
        try {
        	claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(authorization);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new UnAuthorizedException();
        }
        Claims body = claims.getBody();
        log.info("value : {}", body);
        return body.get("userId", String.class);
	}
}
