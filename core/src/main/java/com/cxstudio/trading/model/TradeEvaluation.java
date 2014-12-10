package com.cxstudio.trading.model;

public class TradeEvaluation {
    private float buyScore;
    private float sellScore;

    public TradeEvaluation(float buyScore, float sellScore) {
        super();
        this.buyScore = buyScore;
        this.sellScore = sellScore;
    }

    public float getBuyScore() {
        return buyScore;
    }

    public float getSellScore() {
        return sellScore;
    }

}
