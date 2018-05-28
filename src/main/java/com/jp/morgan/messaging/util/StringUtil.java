package com.jp.morgan.messaging.util;

import java.math.BigDecimal;

public class StringUtil {

    public static BigDecimal parseFloat(final String price) {
        return new BigDecimal(price.replace("p", ""));
    }
}
