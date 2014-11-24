package com.cxstudio.trading.model;

import java.util.Date;

public interface ClosedPosition extends OpenPosition {
    public Date getCloseDate();

    public float getClosePrice();
}
