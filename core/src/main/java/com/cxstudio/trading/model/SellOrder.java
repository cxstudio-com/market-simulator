package com.cxstudio.trading.model;


public class SellOrder implements Order {
    private Symbol symbol;
    private int numOfShares;
    private OrderType orderType;
    private float highPrice;
    private float lowPrice;

    public SellOrder(Symbol symbol, int numOfShares, OrderType orderType) {
        this.symbol = symbol;
        this.numOfShares = numOfShares;
        this.orderType = orderType;
    }

    public SellOrder(Symbol symbol, int numOfShares, OrderType orderType, float highPrice, float lowPrice) {
        this.symbol = symbol;
        this.numOfShares = numOfShares;
        this.orderType = orderType;
        this.highPrice = highPrice;
        this.lowPrice = lowPrice;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public int getNumOfShares() {
        return numOfShares;
    }

    public OrderType getOrderType() {
        return orderType;
    }

	public float getHighPrice() {
        return highPrice;
    }

    public float getLowPrice() {
        return lowPrice;
    }

    @Override
	public String toString() {
		return "SellOrder [symbol=" + symbol + ", numOfShares=" + numOfShares
				+ ", orderType=" + orderType + ", highPrice=" + highPrice
				+ ", lowPrice=" + lowPrice + "]";
	}
}
