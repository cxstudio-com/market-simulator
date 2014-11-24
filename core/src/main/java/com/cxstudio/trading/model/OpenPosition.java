package com.cxstudio.trading.model;

import java.util.Date;

public interface OpenPosition {

    public Symbol getSymbol();

    public Date getEntryDate();

    public float getEntryPrice();

}
