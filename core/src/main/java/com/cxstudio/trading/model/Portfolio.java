package com.cxstudio.trading.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Portfolio {
    static Logger log = LoggerFactory.getLogger(Portfolio.class);
    private Set<OpenPosition> openPositions;
    private Set<ClosedPosition> closedPositions;
    private Map<Symbol, Set<OpenPosition>> mappedOpenPositions;
    private Map<Symbol, Set<ClosedPosition>> mappedClosedPositions;
    private float availableCash;

    public Portfolio() {
        this.closedPositions = new TreeSet<ClosedPosition>();
        this.openPositions = new TreeSet<OpenPosition>();
        this.mappedOpenPositions = new HashMap<Symbol, Set<OpenPosition>>();
        this.mappedClosedPositions = new HashMap<Symbol, Set<ClosedPosition>>();
    }

    public Portfolio(float cash) {
        this();
        this.availableCash = cash;
    }

    /**
     * Get an ordered list of open positions
     * 
     * @return
     */
    public Set<OpenPosition> getOpenPositions() {
        return Collections.unmodifiableSet(openPositions);
    }

    public void addOpenPosition(OpenPosition openPosition) {
        openPositions.add(openPosition);
        if (!mappedOpenPositions.containsKey(openPosition.getSymbol())) {
            mappedOpenPositions.put(openPosition.getSymbol(), new TreeSet<OpenPosition>());
        }
        mappedOpenPositions.get(openPosition.getSymbol()).addAll(openPositions);
        this.availableCash -= openPosition.getNumOfShares() * openPosition.getEntryPrice();
    }

    public void closePosition(ClosedPosition position) {
        TreeSet<OpenPosition> exPositions = (TreeSet<OpenPosition>) mappedOpenPositions.get(position.getSymbol());
        int sharesToSell = getSharesPerSymbol(position.getSymbol());
        int sharesToRemove = sharesToSell;
        if (exPositions != null) {
            while (sharesToRemove > 0) {
                OpenPosition head = exPositions.first();
                if (head.getNumOfShares() <= sharesToRemove) {
                    exPositions.remove(head);
                    openPositions.remove(head);
                    sharesToRemove -= head.getNumOfShares();
                } else { // pos.getNumOfShares() > sharesToRemove
                    head.setNumOfShares(head.getNumOfShares() - sharesToRemove);
                    sharesToRemove = 0;
                }
            }
            addClosedPosition(position);
            this.availableCash += sharesToSell * position.getClosePrice();
        }

    }

    public int getSharesPerSymbol(Symbol symobl) {
        Set<OpenPosition> exPositions = mappedOpenPositions.get(symobl);
        int totalShares = 0;
        if (exPositions != null) {
            for (OpenPosition position : exPositions) {
                totalShares += position.getNumOfShares();
            }
        }
        return totalShares;
    }

    public float totalCostPerSymbol(Symbol symbol) {
        Set<OpenPosition> exPositions = mappedOpenPositions.get(symbol);
        float sum = 0f;
        if (exPositions != null) {
            for (OpenPosition position : exPositions) {
                sum += position.getNumOfShares() * position.getEntryPrice();
            }
        }
        return sum;
    }

    /**
     * Get an ordered list of closed positions
     * 
     * @return
     */
    public Set<ClosedPosition> getClosedPositions() {
        return Collections.unmodifiableSet(closedPositions);
    }

    private void addClosedPosition(ClosedPosition position) {
        closedPositions.add(position);
        if (!mappedClosedPositions.containsKey(position.getSymbol())) {
            mappedClosedPositions.put(position.getSymbol(), new TreeSet<ClosedPosition>());
        }
        mappedClosedPositions.get(position.getSymbol()).add(position);
    }

    public float getAvailableCash() {
        return availableCash;
    }

    public void setAvailableCash(float availableCash) {
        this.availableCash = availableCash;
    }

    public Set<Symbol> getHoldingSymbols() {
        return mappedOpenPositions.keySet();
    }

    @Override
    public String toString() {
        return "[open=" + openPositions.size() + ", closed=" + closedPositions.size() + ", Cash=" + availableCash + "]";
    }

}
