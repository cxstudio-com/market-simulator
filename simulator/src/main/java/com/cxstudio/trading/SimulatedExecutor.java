package com.cxstudio.trading;

import org.springframework.stereotype.Component;

import com.cxstudio.trading.model.Order;
import com.cxstudio.trading.model.Portfolio;
import com.cxstudio.trading.model.Position;

@Component
public class SimulatedExecutor implements OrderExecutor {

    public Position execute(Order order, Portfolio profile) {
        // TODO Auto-generated method stub
        return null;
    }

}
