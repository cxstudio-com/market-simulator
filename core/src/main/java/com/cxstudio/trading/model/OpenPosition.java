package com.cxstudio.trading.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OpenPosition implements Position, Comparable {
    private Symbol symbol;
    private Date entryDate;
    private float entryPrice;
    private int numOfShares;
    private float fee;

    public Symbol getSymbol() {
        return symbol;
    }

    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public float getEntryPrice() {
        return entryPrice;
    }

    public void setEntryPrice(float entryPrice) {
        this.entryPrice = entryPrice;
    }

    public int getNumOfShares() {
        return numOfShares;
    }

    public void setNumOfShares(int numOfShares) {
        this.numOfShares = numOfShares;
    }

    public float getFee() {
        return fee;
    }

    public void setFee(float fee) {
        this.fee = fee;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((entryDate == null) ? 0 : entryDate.hashCode());
        result = prime * result + Float.floatToIntBits(entryPrice);
        result = prime * result + numOfShares;
        result = prime * result + ((symbol == null) ? 0 : symbol.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        OpenPosition other = (OpenPosition) obj;
        if (entryDate == null) {
            if (other.entryDate != null)
                return false;
        } else if (!entryDate.equals(other.entryDate))
            return false;
        if (Float.floatToIntBits(entryPrice) != Float.floatToIntBits(other.entryPrice))
            return false;
        if (numOfShares != other.numOfShares)
            return false;
        if (symbol == null) {
            if (other.symbol != null)
                return false;
        } else if (!symbol.equals(other.symbol))
            return false;
        return true;
    }

    public int compareTo(Object anotherOpenPosition) {
        return this.getEntryDate().compareTo(((OpenPosition) anotherOpenPosition).getEntryDate());
    }

    @Override
    public String toString() {
        DateFormat format = new SimpleDateFormat("MM/dd/yyy");
        return "[" + format.format(entryDate) + ", " + symbol.getTicker() + ": $" + entryPrice + " X " + numOfShares
                + " shares, fee=" + fee + "]";
    }

}
