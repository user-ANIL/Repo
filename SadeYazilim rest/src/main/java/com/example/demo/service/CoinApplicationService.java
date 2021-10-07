package com.example.demo.service;

import com.example.demo.model.CoinInfo;
import com.example.demo.repository.CoinRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

@Service
public class CoinApplicationService {
    private final CoinRepository m_coinRepository;
    private ArrayList<CoinInfo> m_coinInfos;
    private final ArrayList<CoinInfo> m_btcList;
    private final ArrayList<CoinInfo> m_ethList;
    private CoinInfo m_btc, m_bnb, m_eth, m_xrp, m_bch, m_ltc;


    @Value("${time.period.refresh}")
    public long m_period;

    @Value("${time.avg}")
    public long m_avg;

    @Value("${time.type}")
    public String m_type;



    public CoinApplicationService(CoinRepository coinRepository) {
        m_btcList = new ArrayList<>();
        m_ethList = new ArrayList<>();

        m_coinRepository = coinRepository;
        init();

    }

    public void init() {

        setCoinInfo();



        if (m_coinRepository.count() != 6) {
            m_btc.setTitle("Bitcoin");
            m_coinRepository.save(m_btc);
            m_bnb.setTitle("Binance Coin");
            m_coinRepository.save(m_bnb);
            m_eth.setTitle("Ethereum");
            m_coinRepository.save(m_eth);
            m_xrp.setTitle("Ripple");
            m_coinRepository.save(m_xrp);
            m_bch.setTitle("Bitcoin Cash");
            m_coinRepository.save(m_bch);
            m_ltc.setTitle("Litecoin");
            m_coinRepository.save(m_ltc);
            System.out.println("sql database created");
        }
    }

    @PostConstruct
    public void update() {
        Timer timer = new Timer();
        chooseEnum();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                setCoinInfo();

                m_btc.setLocalTime(LocalTime.now());
                m_eth.setLocalTime(LocalTime.now());


                if (m_btcList.size() < m_avg / (m_period * 1000)) {
                    m_btcList.add(m_btc);
                    m_ethList.add(m_eth);
                } else {
                    shiftList(m_btcList, m_btc);
                    shiftList(m_ethList, m_eth);
                }
                fillRepository();


            }
        }, 0, m_period * 1000);
    }

    private void fillRepository()
    {
        m_coinRepository.update(m_btc);
        m_coinRepository.update(m_bnb);
        m_coinRepository.update(m_eth);
        m_coinRepository.update(m_xrp);
        m_coinRepository.update(m_bch);
        m_coinRepository.update(m_ltc);
    }

    private void setCoinInfo() {
        m_coinInfos = m_coinRepository.findAllAsList();
        m_btc = m_coinInfos.get(688);
        m_bnb = m_coinInfos.get(689);
        m_eth = m_coinInfos.get(691);
        m_xrp = m_coinInfos.get(692);
        m_bch = m_coinInfos.get(667);
        m_ltc = m_coinInfos.get(190);
    }

    @Bean
    public ArrayList<CoinInfo> getBtcList() {
        return m_btcList;
    }

    @Bean
    public ArrayList<CoinInfo> getEthList() {
        return m_ethList;
    }


    private void shiftList(ArrayList<CoinInfo> list, CoinInfo c) {
        list.add(0, c);
        list.remove(list.size() - 1);
    }

    private void chooseEnum() {
        switch (m_type) {
            case "SECONDS":
                m_avg *= 1000;
                break;
            case "MINUTES":
                m_avg *= 60000;
                break;
            case "HOURS":
                m_avg *= 3600000;
        }
    }

}
