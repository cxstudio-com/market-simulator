package com.cxstudio.trading.model;

import java.util.Date;

public class ClosedPosition implements Position, Comparable {
    private Symbol symbol;
    private Date entryDate;
    private float entryPrice;
    private Date closeDate;
    private float closePrice;
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

    public Date getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(Date closeDate) {
        this.closeDate = closeDate;
    }

    public float getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(float closePrice) {
        this.closePrice = closePrice;
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
		result = prime * result
				+ ((closeDate == null) ? 0 : closeDate.hashCode());
		result = prime * result + Float.floatToIntBits(closePrice);
		result = prime * result
				+ ((entryDate == null) ? 0 : entryDate.hashCode());
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
		ClosedPosition other = (ClosedPosition) obj;
		if (closeDate == null) {
			if (other.closeDate != null)
				return false;
		} else if (!closeDate.equals(other.closeDate))
			return false;
		if (Float.floatToIntBits(closePrice) != Float
				.floatToIntBits(other.closePrice))
			return false;
		if (entryDate == null) {
			if (other.entryDate != null)
				return false;
		} else if (!entryDate.equals(other.entryDate))
			return false;
		if (Float.floatToIntBits(entryPrice) != Float
				.floatToIntBits(other.entryPrice))
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

	public int compareTo(Object anotherClosedPosition) {
		return this.getCloseDate().compareTo(((ClosedPosition)anotherClosedPosition).getCloseDate());
	}

}
