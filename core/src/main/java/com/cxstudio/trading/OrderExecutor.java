package com.cxstudio.trading;

import com.cxstudio.trading.model.Order;
import com.cxstudio.trading.model.Position;
import com.cxstudio.trading.model.Portfolio;

public interface OrderExecutor {

    public Position execute(Order order, Portfolio profile);
}
