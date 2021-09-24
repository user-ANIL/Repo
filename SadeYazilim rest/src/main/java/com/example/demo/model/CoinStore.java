package com.example.demo.model;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class CoinStore {

    private CoinInfo[] m_coinInfos;

    public void setCoinInfo(CoinInfo[] coinInfos) {
        m_coinInfos = coinInfos;
    }

    public CoinInfo[] getCoinInfo() {
        return m_coinInfos;
    }

}
