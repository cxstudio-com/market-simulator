package com.cxstudio.trading.model;

import java.util.ArrayList;
import java.util.List;

public class Portfolio {
    private List<OpenPosition> openPositions;
    private List<ClosedPosition> closedPositions;
    private float availableCash;

    public Portfolio() {
    	this.closedPositions = new ArrayList<ClosedPosition>();
    	this.openPositions = new ArrayList<OpenPosition>();
    }

    public Portfolio(float cash) {
    	this();
        this.availableCash = cash;
    }

    public List<OpenPosition> getOpenPositions() {
        return openPositions;
    }

    public void setOpenPositions(List<OpenPosition> openPositions) {
        this.openPositions = openPositions;
    }
    
    public List<ClosedPosition> getClosedPositions() {
        return closedPositions;
    }

    public void setClosedPositions(List<ClosedPosition> closedPositions) {
        this.closedPositions = closedPositions;
    }

    public float getAvailableCash() {
        return availableCash;
    }

    public void setAvailableCash(float availableCash) {
        this.availableCash = availableCash;
    }

}
