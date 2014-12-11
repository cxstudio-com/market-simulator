package com.cxstudio.trading.model;

import java.util.Date;

public interface Position {

    public Symbol getSymbol();

    public Date getEntryDate();

    public float getEntryPrice();

    public int getNumOfShares();

}
