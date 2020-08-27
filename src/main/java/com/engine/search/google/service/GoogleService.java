package com.engine.search.google.service;

import com.engine.search.google.json.Result;
import com.engine.search.util.HttpUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GoogleService {

    private final HttpUtil httpUtil;

    @Value("${google.key}")
    private String apiKey;

    public GoogleService(HttpUtil httpUtil) {
        this.httpUtil = httpUtil;
    }

    public List<Result> search(List<String> keywords) {

        Logger log = LoggerFactory.getLogger(GoogleService.class);
        List<Result> resultList = new ArrayList<>();

        if (keywords.size() == 0) {
            return resultList;
        }

        StringBuilder query = new StringBuilder(keywords.get(0));

        for (int i = 1; i < keywords.size(); i++) {
            query.append(" ").append(keywords.get(i));
        }

        String url = "https://www.googleapis.com/customsearch/v1?key=" + apiKey + "&cx=013036536707430787589:_pqjad5hr1a&q=" + query + "&alt=json";

        String jsonResult = httpUtil.getRequest(url);
        JSONObject data = new JSONObject(jsonResult);
        JSONArray items = data.getJSONArray("items");

        int topIndex = 0;
        int index = 0;
        while (topIndex < 10 && topIndex < items.length()) {
            JSONObject item = items.getJSONObject(index);
            Result result = new Result();
            result.link = item.get("link").toString();
            result.title = item.get("title").toString();
            result.image = item.getJSONObject("pagemap").getJSONArray("cse_image").getJSONObject(0).get("src").toString();
            try {
                result.document = Jsoup.connect(result.link).ignoreContentType(true).get();
                index++;
                topIndex++;
            } catch (Exception e) {
                index++;
                topIndex++;
                log.error("document unparsable for link {} ", result.link);
                continue;
            }
            resultList.add(result);
        }
        return resultList;
    }


}
