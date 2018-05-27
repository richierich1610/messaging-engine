package com.jp.morgan.messaging.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum SaleOperationType {
    ADD, SUBTRACT, MULTIPLY;

    public static List<String> getOperations() {
        return new ArrayList<SaleOperationType>(Arrays.asList(SaleOperationType.values())).stream().map(e -> e.name()).collect(Collectors.toList());
    }
}
