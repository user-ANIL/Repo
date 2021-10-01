package com.example.demo.configuration;

import com.example.demo.model.CoinInfo;
import com.example.demo.model.CoinStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.Timer;
import java.util.TimerTask;

@Component
public class TemplateConfig {

    @Value("${json.url}")
    private String m_urlCoin;

    private final CoinStore m_cs;

    public TemplateConfig() {
        m_cs = new CoinStore();
        m_cs.setCoinInfo(new RestTemplate().getForObject("https://api.binance.com/api/v1/ticker/price", CoinInfo[].class));
    }

    @PostConstruct
    public void consumer() {
        Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                m_cs.setCoinInfo(new RestTemplate().getForObject("https://api.binance.com/api/v1/ticker/price", CoinInfo[].class));
            }
        }, 0, 3000);
    }

    @Bean
    @Scope("prototype")
    public CoinStore getIs() {
        System.out.println("getIs");
        return m_cs;
    }
}
