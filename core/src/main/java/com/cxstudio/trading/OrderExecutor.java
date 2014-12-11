package com.cxstudio.trading;

import com.cxstudio.trading.model.Order;
import com.cxstudio.trading.model.Position;

public interface OrderExecutor {

    public Position executeOrder(Order order);
}
