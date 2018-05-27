package com.jp.morgan.messaging.consumer;

import java.util.Objects;

import com.jp.morgan.messaging.model.Message;
import com.jp.morgan.messaging.model.ProductType;
import com.jp.morgan.messaging.model.Sale;
import com.jp.morgan.messaging.model.SaleOperationType;
import com.jp.morgan.messaging.service.IDataService;
import com.jp.morgan.messaging.service.SalesDataService;
import com.jp.morgan.messaging.util.StringUtil;

public class SaleMessageConsumer extends AbstractMessageConsumer {
    private IDataService DEFAULT_DATA_SERVICE = new SalesDataService();
    private IDataService dataService;
    
    

    public SaleMessageConsumer()
    {
    	this.dataService = DEFAULT_DATA_SERVICE;
    }
    
    public SaleMessageConsumer(IDataService dataService) {
        this.dataService = dataService != null ? dataService : DEFAULT_DATA_SERVICE;
    }
    
    
    

    @Override
    public boolean doProcess(Message message) {
        Objects.requireNonNull(message, "Message can not be null");
        return processMessage(message.getMessage());
    }

    protected boolean processMessage(final String message) {
        Objects.requireNonNull(message, "Sale Message can not be empty");
        try {
            final String[] values = message.split(" ");

            if (SaleOperationType.getOperations().contains(values[0].toUpperCase())) {
                //Add 20p apples would instruct your application to add 20p to each sale of apples you have recorded.
                dataService.doAdjustment(SaleOperationType.valueOf(values[0].toUpperCase()),StringUtil.parseFloat(values[1]),values[2].toUpperCase());
            } else if (values[0].matches("\\d+")) {
                //20 sales of apples at 10p each
                int value = Integer.parseInt(values[0]);
                for (int i = 0; i < value; i++) {
                    dataService.doRegisterSale(new Sale(new ProductType(values[3].toUpperCase()), StringUtil.parseFloat(values[5])));
                }
            } else {
                //apple at 10p
                dataService.doRegisterSale(new Sale(new ProductType(values[0].toUpperCase() + "S"), StringUtil.parseFloat(values[2])));
            }
            return true;
        } catch (Exception ex) {
           System.out.println("Logging:--- System was not able to record sale : -  "+message);
           return false;
        }

    }
    
    
    
    @Override
    public void logReport(final int messageCount) {
            dataService.logReport(messageCount,this.shouldPause(messageCount));
    }
}