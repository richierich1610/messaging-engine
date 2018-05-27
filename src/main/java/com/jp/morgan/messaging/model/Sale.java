package com.jp.morgan.messaging.model;

public class Sale {
    private ProductType productType;
    private float value;

    public Sale(ProductType productType, float value) {
        this.productType = productType;
        this.value = value;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
}