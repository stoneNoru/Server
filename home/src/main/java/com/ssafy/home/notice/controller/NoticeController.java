package com.ssafy.home.notice.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.home.notice.dto.Item;
import com.ssafy.home.notice.dto.NewsDto;
import com.ssafy.home.notice.dto.NoticeDto;
import com.ssafy.home.notice.dto.ResponseNewsDto;
import com.ssafy.home.notice.model.service.NoticeService;
import com.ssafy.home.util.ResultDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/notices")
@CrossOrigin("*")
@RequiredArgsConstructor
@Slf4j
public class NoticeController {
	private final NoticeService noticeService;
	@Value("${clientId}")
	private String clientId; //애플리케이션 클라이언트 아이디
	@Value("${clientSecret}")
	private String clientSecret; //애플리케이션 클라이언트 시크릿

	@GetMapping
	public ResponseEntity<?> selectAll() {
		List<NoticeDto> list = noticeService.selectAll();

		return ResponseEntity.ok(ResultDto.res(HttpStatus.OK.value(), "공지 사항입니다.",list));
	}

	@GetMapping("/news")
	public ResponseEntity<?> findNews() throws JsonMappingException, JsonProcessingException {

		String text = null;
		try {
			text = URLEncoder.encode("부동산", "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("검색어 인코딩 실패",e);
		}


		String apiURL = "https://openapi.naver.com/v1/search/news?query=" + text;    // JSON 결과

		Map<String, String> requestHeaders = new HashMap<>();
		requestHeaders.put("X-Naver-Client-Id", clientId);
		requestHeaders.put("X-Naver-Client-Secret", clientSecret);
		String responseBody = get(apiURL,requestHeaders);

		ObjectMapper objectMapper = new ObjectMapper();

        // JSON 문자열을 Java 객체로 매핑
		NewsDto newsResponse = objectMapper.readValue(responseBody, NewsDto.class);
		
		List<ResponseNewsDto> list = new ArrayList<>();
		
		for(Item i:newsResponse.getItems()) {
			ResponseNewsDto res = ResponseNewsDto.builder()
					.title(i.getTitle())
					.link(i.getLink())
					.description(i.getDescription())
					.pubDate(i.getPubDate())
					.build();
			list.add(res);
		}
		log.debug(list.get(0).toString());
		return ResponseEntity.ok(ResultDto.res(HttpStatus.OK.value(), "뉴스입니다.", list));
	}

	private static String get(String apiUrl, Map<String, String> requestHeaders){
		HttpURLConnection con = connect(apiUrl);
		try {
			con.setRequestMethod("GET");
			for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
				con.setRequestProperty(header.getKey(), header.getValue());
			}


			int responseCode = con.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
				return readBody(con.getInputStream());
			} else { // 오류 발생
				return readBody(con.getErrorStream());
			}
		} catch (IOException e) {
			throw new RuntimeException("API 요청과 응답 실패", e);
		} finally {
			con.disconnect();
		}
	}


	private static HttpURLConnection connect(String apiUrl){
		try {
			URL url = new URL(apiUrl);
			return (HttpURLConnection)url.openConnection();
		} catch (MalformedURLException e) {
			throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
		} catch (IOException e) {
			throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
		}
	}


	private static String readBody(InputStream body){
		InputStreamReader streamReader = new InputStreamReader(body);


		try (BufferedReader lineReader = new BufferedReader(streamReader)) {
			StringBuilder responseBody = new StringBuilder();


			String line;
			while ((line = lineReader.readLine()) != null) {
				responseBody.append(line);
			}


			return responseBody.toString();
		} catch (IOException e) {
			throw new RuntimeException("API 응답을 읽는 데 실패했습니다.", e);
		}
	}

}
