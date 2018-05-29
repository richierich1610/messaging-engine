package com.jp.morgan.messaging.util;

import java.math.BigDecimal;

public class StringUtil {

    public static BigDecimal parsePriceString(final String price) {
        return new BigDecimal(price.replace("p", ""));
    }
}
