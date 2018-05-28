package com.jp.morgan.messaging.service;

import java.math.BigDecimal;

import com.jp.morgan.messaging.model.Sale;
import com.jp.morgan.messaging.model.SaleOperationType;

public interface IDataService {
    public void logReport(long messageCount,boolean systemPaused);

    public boolean doAdjustment(SaleOperationType operation,BigDecimal value,String productType);

    public boolean doRegisterSale(Sale sale);
}
