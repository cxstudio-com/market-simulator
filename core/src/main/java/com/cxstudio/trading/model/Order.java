package com.cxstudio.trading.model;

public interface Order {

    public Symbol getSymbol();

    public int getNumOfShares();

    public OrderType getOrderType();

    public float getHighPrice();

    public float getLowPrice();

    public static enum OrderType {
        MARKET, STOP, LIMIT, STOP_LIMIT
    }
}
