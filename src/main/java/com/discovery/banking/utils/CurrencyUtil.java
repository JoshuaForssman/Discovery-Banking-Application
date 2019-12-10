package com.discovery.banking.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class CurrencyUtil {

    public static BigDecimal calculateZar(BigDecimal conversionRate, String conversionIndicator, BigDecimal value, int decimalPlaces){

        if(conversionIndicator.equals("/")){
            return  value.divide(conversionRate, decimalPlaces, RoundingMode.HALF_DOWN);
        }else {
            return value.multiply(conversionRate.setScale( decimalPlaces, RoundingMode.HALF_DOWN));
        }
    }

    //public static List<String> determineOptimal

}
