package com.discovery.banking.utils;

import com.discovery.banking.wrapper.DenominationCountValueWrapper;

import javax.persistence.criteria.CriteriaBuilder;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class CurrencyUtil {

    public static BigDecimal calculateZar(BigDecimal conversionRate, String conversionIndicator, BigDecimal value, int decimalPlaces){

        if(conversionIndicator.equals("/")){
            return  value.divide(conversionRate, decimalPlaces, RoundingMode.HALF_DOWN);
        }else if(conversionIndicator.equals("*")){
            return value.multiply(conversionRate.setScale( decimalPlaces, RoundingMode.HALF_DOWN));
        }else if(conversionIndicator.equals("+")){
            return value.add(conversionRate.setScale( decimalPlaces, RoundingMode.HALF_DOWN));
        }else if(conversionIndicator.equals("-")){
            return value.subtract(conversionRate.setScale( decimalPlaces, RoundingMode.HALF_DOWN));
        }else {
            throw new ArithmeticException("Incorrect operator " + conversionIndicator);
        }
    }



}
