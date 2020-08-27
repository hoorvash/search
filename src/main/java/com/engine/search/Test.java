package com.engine.search;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class Test {

    public static void main1(String[] args) {
        List<String> list = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        list.add("hello");
        list.add("salam");
        try {
            System.out.println(mapper.writeValueAsString(list));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
//        String google = "http://www.google.com/search?q=";
//        String search = "stackoverflow";
//        String charset = "UTF-8";
//        String userAgent = "ExampleBot 1.0 (+http://example.com/bot)";
        String key="AIzaSyAm1Dr4lE9LdcYZg-zA-airZ9QdMifvTEM";
        String qry="Android";
        URL url = new URL(
                "https://www.googleapis.com/customsearch/v1?key="+key+ "&cx=013036536707430787589:_pqjad5hr1a&q="+ qry + "&alt=json");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");
        BufferedReader br = new BufferedReader(new InputStreamReader(
                (conn.getInputStream())));

        String output;
        System.out.println("Output from Server .... \n");
        while ((output = br.readLine()) != null) {

            if(output.contains("\"link\": \"")){
                String link=output.substring(output.indexOf("\"link\": \"")+("\"link\": \"").length(), output.indexOf("\","));
                System.out.println(link);       //Will print the google search links
            }
        }
        conn.disconnect();

    }
}
