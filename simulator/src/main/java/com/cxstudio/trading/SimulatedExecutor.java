package com.cxstudio.trading;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.cxstudio.trading.model.BuyOrder;
import com.cxstudio.trading.model.ClosedPosition;
import com.cxstudio.trading.model.OpenPosition;
import com.cxstudio.trading.model.Order;
import com.cxstudio.trading.model.Portfolio;
import com.cxstudio.trading.model.Position;
import com.cxstudio.trading.model.SellOrder;

@Component
public class SimulatedExecutor implements OrderExecutor {
	private float transactionFee = 15.0f;
	
	
    public Position execute(Order order, Portfolio portfolio) {
    	if (order instanceof BuyOrder) {
    		BuyOrder buyOrder = (BuyOrder) order;
    		float totalCost = buyOrder.getNumOfShares() * buyOrder.getHighPrice() + transactionFee;
    		
    		if (portfolio.getAvailableCash() > totalCost) {
    			OpenPosition newPos = new OpenPosition();
    			newPos.setSymbol(order.getSymbol());
    			newPos.setEntryDate(new Date());
    			newPos.setEntryPrice(buyOrder.getHighPrice());
    			newPos.setNumOfShares(buyOrder.getNumOfShares());
    			portfolio.setAvailableCash(portfolio.getAvailableCash() - totalCost);
    			portfolio.getOpenPositions().add(newPos);
    		} else {
    			throw new RuntimeException("Not enough available cash to execute order.");
    		}    		
    	} else if (order instanceof SellOrder) {
    		SellOrder sellOrder = (SellOrder) order;
    		OpenPosition newPos = new ClosedPosition();
			newPos.setSymbol(order.getSymbol());
			newPos.setEntryDate(new Date());
			newPos.setEntryPrice(buyOrder.getHighPrice());
			newPos.setNumOfShares(buyOrder.getNumOfShares());
			portfolio.setAvailableCash(portfolio.getAvailableCash() - totalCost);
			portfolio.getOpenPositions().add(newPos);
    		
    	}
    	
    	
    	
        if (profile.getAvailableCash() > order.getNumOfShares() * order. 
        return null;
    }

}
