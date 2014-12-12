package com.cxstudio.trading.model;

import java.util.List;

public class Portfolio {
    private List<OpenPosition> openPositions;
    private List<ClosedPosition> closedPosition;
    private float availableCash;

    public Portfolio() {

    }

    public Portfolio(float cash) {
        this.availableCash = cash;
    }

    public List<OpenPosition> getOpenPositions() {
        return openPositions;
    }

    public void setOpenPositions(List<OpenPosition> openPositions) {
        this.openPositions = openPositions;
    }

    public List<ClosedPosition> getClosedPosition() {
        return closedPosition;
    }

    public void setClosedPosition(List<ClosedPosition> closedPosition) {
        this.closedPosition = closedPosition;
    }

    public float getAvailableCash() {
        return availableCash;
    }

    public void setAvailableCash(float availableCash) {
        this.availableCash = availableCash;
    }

}
