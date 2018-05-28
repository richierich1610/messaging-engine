package com.jp.morgan.messaging.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jp.morgan.messaging.model.Sale;
import com.jp.morgan.messaging.model.SaleOperationType;
import com.jp.morgan.messaging.util.Config;

public class SalesDataService implements IDataService {
	
	private static final int DEFAULT_SALES_REPORT_FREQUENCY = Config.APP_CONFIG.get("sales_report_frequency").getAsInt();
	private static final int DEFAULT_ADJUSTMENT_REPORT_FREQUENCY = Config.APP_CONFIG.get("adjustment_report_frequency").getAsInt();
    private static Map<String, List<String>> ADJUSTMENT_SALE_MAP = new HashMap<>(1000);
    private static Map<String, List<Sale>> SALE_MAP = new HashMap<>(1000);
    

    @Override
    public void logReport(long messageCount,boolean systemPaused) {

    	List<String> reportTypes = getReportType(messageCount,systemPaused);
        if (reportTypes.contains("SalesReport")) {
            System.out.println("--------------------------------- START Sales Report ---------------------------------");
            for (Map.Entry<String, List<Sale>> entry : SALE_MAP.entrySet()) {
                List<Sale> sales = SALE_MAP.get(entry.getKey());
                BigDecimal value = sales.stream().map(e -> e.getValue()).reduce(BigDecimal.ZERO, BigDecimal::add);
                System.out.println(String.format("[%s] product type having total sale [%d] of total value [%f]", entry.getKey(), sales.size(), value));
            }
            System.out.println("--------------------------------- END Sales Report ---------------------------------");
        }
        if (reportTypes.contains("AdjustmentReport")) {
            System.out.println("--------------------------------- START Adjustment Report ---------------------------------");
            for (Map.Entry<String, List<String>> entry : ADJUSTMENT_SALE_MAP.entrySet()) {
                List<String> sales = ADJUSTMENT_SALE_MAP.get(entry.getKey());
                System.out.println(String.format("-------------------[%s] Adjustments-------------------", entry.getKey()));
                sales.stream().forEach(s -> {
                    System.out.println(String.format("\t\t[%s]", s));
                });
            }
            System.out.println("--------------------------------- END Adjustment Report -----------------------------------");
        }
    }

    public boolean doAdjustment(SaleOperationType operation,BigDecimal value,String productType) {
        List<Sale> sales = SALE_MAP.get(productType);
        if (null != sales) {
            switch (operation) {
                case ADD:
                    sales.stream().forEach(e -> e.setValue(e.getValue().add(value)));
                    break;
                case SUBTRACT:
                    sales.stream().forEach(e -> e.setValue(e.getValue().subtract(value)));
                    break;
                case MULTIPLY:
                    sales.stream().forEach(e -> e.setValue(e.getValue().multiply(value)));
                    break;
                default:
                    System.out.println("Logging: - Invalid operation specified.");
                    return false;
            }
            List<String> adjustmentMessage = ADJUSTMENT_SALE_MAP.get(productType);
            adjustmentMessage = adjustmentMessage != null ? adjustmentMessage : new ArrayList<>();
            adjustmentMessage.add(operation.name() + " " + value);
            ADJUSTMENT_SALE_MAP.put(productType, adjustmentMessage);
        }
        return true;
    }

    public boolean doRegisterSale(Sale sale) {
        List<Sale> sales = SALE_MAP.get(sale.getProductType().getType());
        sales = sales != null ? sales : Collections.synchronizedList(new ArrayList<>(100000));
        sales.add(sale);
        SALE_MAP.putIfAbsent(sale.getProductType().getType(), sales);
        return true;
    }
    
    private List<String> getReportType(long messageCount,boolean systemPaused) {
        List<String> reports = new ArrayList<>();
        if (messageCount % DEFAULT_SALES_REPORT_FREQUENCY == 0) {
            reports.add("SalesReport");
        }
        if (messageCount % DEFAULT_ADJUSTMENT_REPORT_FREQUENCY == 0 && systemPaused) {
            reports.add("AdjustmentReport");
        }
        return reports;
    }
}
