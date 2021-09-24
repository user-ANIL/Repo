package com.example.demo.configuration;

import com.example.demo.model.CoinInfo;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class JsonConfig {

    private RestTemplate m_restTemplate;

    public JsonConfig(RestTemplate restTemplate) {
        m_restTemplate = restTemplate;
    }

    public Object parse(String url) {
        return m_restTemplate.getForObject(url, CoinInfo.class);
    }
}
