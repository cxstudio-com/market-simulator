package com.cxstudio.trading;

import java.util.Date;
import java.util.List;

import com.cxstudio.trading.model.Trade;

public interface TradeRetriever {

    public Trade retrieve(Date dateTime);

    public List<Trade> lastNumOfTrades(Date startTime, int numOfTrades);

}
