package com.jp.morgan.messaging.model;

import java.math.BigDecimal;

public class Sale {
    private ProductType productType;
    private BigDecimal value;

    public Sale(ProductType productType, BigDecimal value) {
        this.productType = productType;
        this.value = value;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}