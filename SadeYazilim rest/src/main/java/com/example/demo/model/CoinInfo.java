package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalTime;

@JsonIgnoreProperties
public class CoinInfo {
    private long m_id = 0;
    private String m_title = "null";
    private String m_symbol = "null";
    private double m_price = 0;
    private long m_coinId = 1;
    private double average = 0;
    private LocalTime m_localTime = null;

    public LocalTime getLocalTime() {
        return m_localTime;
    }

    public void setLocalTime(LocalTime localTime) {
        this.m_localTime = localTime;
    }

    public double getAverage() {
        return average;
    }

    public void setAverage(double average) {
        this.average = average;
    }

    public long getcoinId() {
        return m_coinId;
    }

    public void setCoinId(long coinId) {
        m_coinId = coinId;
    }

    public long getId() {
        return m_id;
    }

    public void setId(long m_id) {
        this.m_id = m_id;
    }

    public String getTitle() {
        return m_title;
    }

    public void setTitle(String m_title) {
        this.m_title = m_title;
    }

    public String getSymbol() {
        return m_symbol;
    }

    public void setSymbol(String symbol) {
        m_symbol = symbol;
    }

    public double getPrice() {
        return m_price;
    }

    public void setPrice(double price) {
        m_price = price;
    }


    @Override
    public String toString() {
        return "CoinInfo{" + "id = " + m_id +
                "symbol = " + m_symbol + '\'' +
                ", price = " + m_price + "time = " + m_localTime +
                '}';
    }
}
