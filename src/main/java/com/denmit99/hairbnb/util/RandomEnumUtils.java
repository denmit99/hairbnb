package com.denmit99.hairbnb.util;

import org.apache.commons.lang3.RandomUtils;

public final class RandomEnumUtils {

    private RandomEnumUtils() {
    }

    public static <E extends Enum<E>> E nextValue(Class<E> enumClass) {
        E[] enumConstants = enumClass.getEnumConstants();
        return enumConstants[RandomUtils.nextInt(0, enumConstants.length)];
    }
}
