package com.cxstudio.trading;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    static Logger log = LoggerFactory.getLogger(SimulatedExecutor.class);
    private float transactionFee = 15.0f;

    public Position execute(Order order, Portfolio portfolio) {
        Position position = null;
        if (order instanceof BuyOrder) {
            BuyOrder buyOrder = (BuyOrder) order;
            float cost = buyOrder.getNumOfShares() * buyOrder.getHighPrice() + transactionFee;
            log.debug("Adding buy order {}", buyOrder);
            if (portfolio.getAvailableCash() > (cost + transactionFee)) {
                OpenPosition newPos = new OpenPosition();
                newPos.setSymbol(order.getSymbol());
                newPos.setEntryDate(new Date());
                newPos.setEntryPrice(buyOrder.getHighPrice());
                newPos.setNumOfShares(buyOrder.getNumOfShares());
                newPos.setFee(transactionFee);
                portfolio.addOpenPosition(newPos);
                log.debug("New open position created {}", newPos);
                position = newPos;
            } else {
                throw new RuntimeException("Not enough available cash to execute order.");
            }
        } else if (order instanceof SellOrder) {
            SellOrder sellOrder = (SellOrder) order;

            log.debug("Adding sell order {}", sellOrder);
            ClosedPosition newPos = new ClosedPosition();
            newPos.setSymbol(order.getSymbol());
            newPos.setCloseDate(new Date());
            newPos.setEntryPrice(sellOrder.getLowPrice());
            newPos.setNumOfShares(sellOrder.getNumOfShares());
            newPos.setFee(transactionFee);
            newPos.setClosePrice(sellOrder.getLowPrice());
            portfolio.closePosition(newPos);

            log.debug("New closed position created {}", newPos);
            position = newPos;
        }

        return position;
    }

}
