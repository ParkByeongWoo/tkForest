package com.tkForest.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Controller
@RequestMapping("/api")
public class GlobalMapController {
    private static final Logger logger = LoggerFactory.getLogger(GlobalMapController.class);

    @Value("${openApi.serviceKey}")
    private String serviceKey;

    @Value("${openApi.callBackUrl}")
    private String callBackUrl;

    @Value("${openApi.Type}")
    private String Type;

    // 기존 API 엔드포인트에 @ResponseBody 추가
    @GetMapping("/trend")
    @ResponseBody
    public ResponseEntity<String> callForecastApi(
            @RequestParam(value="numOfRows", defaultValue="3") String numOfRows,
            @RequestParam(value="pageNo", defaultValue="1") String pageNo,
            @RequestParam(value="region", required=false) String region,  // 추가된 부분
            @RequestParam(value="search1", required=false) String search1,
            @RequestParam(value="search2", required=false) String search2,
            @RequestParam(value="search3", required=false) String search3,
            @RequestParam(value="search4", required=false) String search4,
            @RequestParam(value="search5", required=false) String search5,
            @RequestParam(value="search6", required=false) String search6,
            @RequestParam(value="search7", required=false) String search7
    ) {
        HttpURLConnection urlConnection = null;
        InputStream stream = null;
        String result = null;

        StringBuilder urlStr = new StringBuilder(callBackUrl)
                .append("serviceKey=").append(serviceKey)
                .append("&type=").append(Type)
                .append("&numOfRows=").append(numOfRows)
                .append("&pageNo=").append(pageNo);

        if (isValidParameter(search1)) urlStr.append("&search1=").append(search1);
        if (isValidParameter(search2)) urlStr.append("&search2=").append(search2);
        if (isValidParameter(search3)) urlStr.append("&search3=").append(search3);
        if (isValidParameter(search4)) urlStr.append("&search4=").append(search4);
        if (isValidParameter(search5)) urlStr.append("&search5=").append(search5);
        if (isValidParameter(search6)) urlStr.append("&search6=").append(search6);
        if (isValidParameter(search7)) urlStr.append("&search7=").append(search7);

        try {
            String finalUrl = urlStr.toString();
            logger.info("Request URL: " + finalUrl);

            URL url = new URL(finalUrl);

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("accept", "*/*");
            urlConnection.setConnectTimeout(10000); // Increase timeout to 10 seconds
            urlConnection.setReadTimeout(10000); // Increase timeout to 10 seconds

            stream = getNetworkConnection(urlConnection);
            result = readStreamToString(stream);

            if (stream != null) stream.close();
        } catch(IOException e) {
            logger.error("Error occurred while calling the API: ", e);
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }

        if (result == null || result.trim().isEmpty()) {
            logger.warn("Empty response from API");
            return new ResponseEntity<>("No data available", HttpStatus.NO_CONTENT);
        }

        // 서버 측에서 region 필터링 추가
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(result);
            JsonNode itemsNode = rootNode.at("/response/body/itemList/item");

            if (itemsNode.isArray() && region != null && !region.isEmpty()) {
                ArrayNode filteredItems = mapper.createArrayNode();
                for (JsonNode item : itemsNode) {
                    if (item.has("regn") && item.get("regn").asText().toLowerCase().contains(region.toLowerCase())) {
                        filteredItems.add(item);
                    }
                }
                ((ObjectNode) rootNode.at("/response/body/itemList")).set("item", filteredItems);
                result = mapper.writeValueAsString(rootNode);
            }

        } catch (JsonProcessingException e) {
            logger.error("Error occurred while processing JSON response: ", e);
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        logger.info("API Response: " + result);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    // HTML 페이지 반환을 위한 매핑 추가
    @GetMapping("/globalMap")
    public String globalMap() {
        return "globalMap";  // templates/globalMap.html 파일을 렌더링합니다.
    }

    /* URLConnection 을 전달받아 연결정보 설정 후 연결, 연결 후 수신한 InputStream 반환 */
    private InputStream getNetworkConnection(HttpURLConnection urlConnection) throws IOException {
        urlConnection.setDoInput(true);

        if(urlConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
            throw new IOException("HTTP error code : " + urlConnection.getResponseCode());
        }

        return urlConnection.getInputStream();
    }

    /* InputStream을 전달받아 문자열로 변환 후 반환 */
    private String readStreamToString(InputStream stream) throws IOException {
        StringBuilder result = new StringBuilder();

        BufferedReader br = new BufferedReader(new InputStreamReader(stream, "UTF-8"));

        String readLine;
        while ((readLine = br.readLine()) != null) {
            result.append(readLine).append("\n");
        }

        br.close();

        return result.toString();
    }

    /* 파라미터가 null이거나 빈 문자열인지 확인 */
    private boolean isValidParameter(String parameter) {
        return parameter != null && !parameter.trim().isEmpty();
    }
}
