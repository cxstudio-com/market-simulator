package com.cxstudio.trading.persistence.db.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cxstudio.trading.model.DataFilter;
import com.cxstudio.trading.model.Symbol;
import com.cxstudio.trading.model.Trade;

public interface TradeMapper {
    List<Trade> selectTrades(@Param("symbol") Symbol symbol, @Param("filter") DataFilter filter);

    void insertTrades(@Param("trades") List<Trade> trades);

    void insertTempTrades(@Param("trades") List<Trade> trades);
}
