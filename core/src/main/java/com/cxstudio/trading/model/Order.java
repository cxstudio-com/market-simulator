package com.cxstudio.trading.model;

import java.util.Date;

public interface Order {

    Symbol getSymbol();

    int getNumOfShares();

    OrderType getOrderType();

    float getHighPrice();

    float getLowPrice();

    Date getCreateDate();

    public static enum OrderType {
        MARKET, STOP, LIMIT, STOP_LIMIT
    }
}
