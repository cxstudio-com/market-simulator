package com.cxstudio.trading.model;

import java.util.Date;

public class BuyOrder implements Order {
    private Symbol symbol;
    private int numOfShares;
    private OrderType orderType;
    private float highPrice;
    private float lowPrice;
    private Date createDate;

    public BuyOrder(Symbol symbol, int numOfShares, OrderType orderType) {
        this(symbol, numOfShares, orderType, 0f, 0f, new Date());
    }

    public BuyOrder(Symbol symbol, int numOfShares, OrderType orderType, float highPrice, float lowPrice, Date createDate) {
        this.symbol = symbol;
        this.numOfShares = numOfShares;
        this.orderType = orderType;
        this.highPrice = highPrice;
        this.lowPrice = lowPrice;
        this.createDate = createDate;
    }

    @Override
    public Symbol getSymbol() {
        return symbol;
    }

    @Override
    public int getNumOfShares() {
        return numOfShares;
    }

    @Override
    public OrderType getOrderType() {
        return orderType;
    }

    @Override
    public float getHighPrice() {
        return highPrice;
    }

    @Override
    public float getLowPrice() {
        return lowPrice;
    }

    @Override
    public Date getCreateDate() {
        return createDate;
    }

    @Override
    public String toString() {
        return "BuyOrder [symbol=" + symbol + ", numOfShares=" + numOfShares + ", orderType=" + orderType + ", highPrice=" + highPrice + ", lowPrice=" + lowPrice + ", createDate=" + createDate + "]";
    }

}
