package com.cxstudio.trading.persistence.db.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.cxstudio.trading.model.DataFilter;
import com.cxstudio.trading.model.Symbol;
import com.cxstudio.trading.model.Trade;

public interface TradeMapper {
    List<Trade> selectTrades(@Param("symbol") Symbol symbol, @Param("filter") DataFilter filter);
    
    List<Trade> selectMiddleBufferedTrades(@Param("symbol") Symbol symbol, @Param("filter") DataFilter filter);

    void insertTrades(@Param("trades") List<Trade> trades);

    void insertTempTrades(@Param("trades") List<Trade> trades);
    
    @Select("SELECT * FROM trade WHERE symbol_id = #{symbol.symbolId} AND datetime BETWEEN #{fromTime} AND #{toTime}")
    @ResultMap("TradeMap")
    Trade queryFirstTradeInRange(@Param("symbol") Symbol symbol, @Param("fromTime") Date fromTime, @Param("toTime") Date toTime); 
}
