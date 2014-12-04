package com.cxstudio.trading.model;

public enum TradeSpacing {
    MINUTE(60), HOUR(60 * 60), DAY(24 * 60 * 60), MONTH(30 * 24 * 60 * 60), YEAR(365 * 24 * 60 * 60);

    private final int spacing;

    private TradeSpacing(int seconds) {
        this.spacing = seconds;
    }

    public int getSeconds() {
        return this.spacing;
    }
    
    public long getMilliseconds() {
    	return this.spacing * 1000l;
    }
}
