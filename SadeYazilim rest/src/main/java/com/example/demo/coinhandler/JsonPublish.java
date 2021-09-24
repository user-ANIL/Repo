package com.example.demo.coinhandler;

import com.example.demo.model.CoinInfo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


import static java.time.LocalTime.*;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class JsonPublish {

    @Value("${time.period.refresh}")
    public long m_period;

    @Value("${time.type}")
    public String m_type;

    @Value(("${time.avg}"))
    public long m_avg;

    private final ArrayList<CoinInfo> m_btcList;
    private final ArrayList<CoinInfo> m_ethList;

    public JsonPublish(@Qualifier("getBtcList") ArrayList<CoinInfo> btcList, @Qualifier("getEthList") ArrayList<CoinInfo> ethList) {
        m_btcList = btcList;
        m_ethList = ethList;
    }

    @GetMapping("/info")
    public ArrayList<CoinInfo> coinInfo() {
        return m_btcList;
    }

    //if it is after m_period * record number second before now get the result
    @GetMapping("/prices")
    public List<CoinInfo> showPrices() {
        LocalTime lt = LocalTime.now().minus(m_period * 3, ChronoUnit.valueOf(m_type));

        return m_btcList.stream().filter(c -> c.getLocalTime().
                isAfter(lt)).
                collect(Collectors.toList());
    }

    @GetMapping("/average/btc")
    public String btcAverage() {
        var result = m_btcList.stream().mapToDouble(CoinInfo::getPrice);
        var print = result.average().getAsDouble();

        return String.format("BTC average price in %d %s : %s", m_avg, m_type.toLowerCase(), print);
    }

    @GetMapping("/average/eth")
    public String ethAverage() {
        var result = m_ethList.stream().mapToDouble(CoinInfo::getPrice);
        var print = result.average().getAsDouble();

        return "ETH average price in 1 hour :" + print;
    }

}
