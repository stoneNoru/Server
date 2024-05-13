package com.ssafy.home.lotto.model.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.home.lotto.dto.Lotto;

@Service
public class LottoServiceImpl implements LottoService {

	@Value("${api.serviceKey}")
	private String serviceKey;
	@Override
	public List<Lotto> selectAll() throws IOException {
		StringBuilder sb = new StringBuilder();
		String urlStr= "https://api.odcloud.kr/api/ApplyhomeInfoDetailSvc/v1/getAPTLttotPblancDetail?"
				+ "page=1&"
				+ "perPage=10&"
				+ "serviceKey="+serviceKey;
		
		URL url = new URL(urlStr);
		
		HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
		urlConnection.setRequestMethod("GET");
		
		BufferedReader br;
		br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
		
		String returnLine;
		while((returnLine = br.readLine())!=null) {
			sb.append(returnLine+"\n\r");
		}
		
		
		
		urlConnection.disconnect();
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		JsonNode rootNode = objectMapper.readTree(sb.toString());
		JsonNode dataNode = rootNode.get("data");

		
		List<Lotto> lottoList = new ArrayList<>();
		if (dataNode.isArray()) {
		    for (JsonNode node : dataNode) {
		        Lotto lotto = objectMapper.treeToValue(node, Lotto.class);
		        lottoList.add(lotto);
		    }
		}
		
		lottoList.forEach(System.out::println);
		
		return lottoList;
	}
	
	@Override
	public Lotto findById(String id) throws IOException {
		String urlStr= "https://api.odcloud.kr/api/ApplyhomeInfoDetailSvc/v1/getAPTLttotPblancDetail?"
				+ "page=1&"
				+ "perPage=10&"
				+ "serviceKey="+serviceKey+"&"
				+ "cond%5BHOUSE_MANAGE_NO%3A%3AEQ%5D="+id
			;
		StringBuilder sb = new StringBuilder();
		URL url = new URL(urlStr);
		
		HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
		urlConnection.setRequestMethod("GET");
		
		BufferedReader br;
		br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
		
		String returnLine;
		while((returnLine = br.readLine())!=null) {
			sb.append(returnLine+"\n\r");
		}
		
		
		
		urlConnection.disconnect();
		
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		JsonNode rootNode = objectMapper.readTree(sb.toString());
		JsonNode dataNode = rootNode.get("data");

		Lotto lotto = objectMapper.treeToValue(dataNode.get(0), Lotto.class);

		
		System.out.println(lotto.toString());
		
		return lotto;
	}

	@Override
	public List<Lotto> findBySido(String sido) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	

}
