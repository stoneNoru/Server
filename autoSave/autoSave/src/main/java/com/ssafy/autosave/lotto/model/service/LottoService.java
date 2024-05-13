package com.ssafy.autosave.lotto.model.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.autosave.lotto.dto.Lotto;
import com.ssafy.autosave.lotto.model.mapper.LottoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LottoService {
    @Value("${api.serviceKey}")
    private String serviceKey;

    private final LottoMapper lottoMapper;

    @Scheduled(cron = "0 0 9 * * *")
//    @Scheduled(fixedRate = 60000)
    public void saveAll() throws IOException {
        List<Lotto> totalLottoList = new ArrayList<>();

        for (int page = 1; page <= 20; page++) {
            StringBuilder sb = new StringBuilder();
            String urlStr= "https://api.odcloud.kr/api/ApplyhomeInfoDetailSvc/v1/getAPTLttotPblancDetail?"
                    + "page=" + page + "&"
                    + "perPage=10&"
                    + "serviceKey=" + serviceKey;

            URL url = new URL(urlStr);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");

            BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
            String returnLine;
            while((returnLine = br.readLine()) != null) {
                sb.append(returnLine).append("\n");
            }
            urlConnection.disconnect();

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            JsonNode rootNode = objectMapper.readTree(sb.toString());
            JsonNode dataNode = rootNode.get("data");

            if (dataNode.isArray()) {
                for (JsonNode node : dataNode) {
                    Lotto lotto = objectMapper.treeToValue(node, Lotto.class);
                    totalLottoList.add(lotto);
                }
            }
        }

        // 전체 데이터를 한 번에 DB에 저장
        lottoMapper.SaveAll(totalLottoList);
    }

}
