package com.jp.morgan.messaging.util;

public class StringUtil {

    public static float parseFloat(final String price) {
        return Float.parseFloat(price.replace("p", ""));
    }
}
