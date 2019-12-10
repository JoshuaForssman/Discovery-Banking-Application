package com.discovery.banking.utils;

import com.discovery.banking.wrapper.DenominationCountValueWrapper;

import javax.persistence.criteria.CriteriaBuilder;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class CurrencyUtil {

    public static BigDecimal calculateZar(BigDecimal conversionRate, String conversionIndicator, BigDecimal value, int decimalPlaces){

        if(conversionIndicator.equals("/")){
            return  value.divide(conversionRate, decimalPlaces, RoundingMode.HALF_DOWN);
        }else {
            return value.multiply(conversionRate.setScale( decimalPlaces, RoundingMode.HALF_DOWN));
        }
    }

    public static DenominationCountValueWrapper determineOptimalDenomination(List<Integer> denominationValues, List<Integer> atmDenominationCount, Double withdrawAmount){

        Integer[] noteValues = new Integer[denominationValues.size()];
        Integer[] noteCounts = new Integer[atmDenominationCount.size()];


        for (int i = denominationValues.size() - 1; i >= 0; i--) {
            // Find denominations
            noteValues[i] = denominationValues.get(i);
            while (withdrawAmount >= denominationValues.get(i)) {
                if(noteCounts[i] == null)
                    noteCounts[i] = 0;

                noteCounts[i] = noteCounts[i] +1;
                withdrawAmount -= denominationValues.get(i);
            }
        }

        DenominationCountValueWrapper denominationCountValueWrapper = new DenominationCountValueWrapper();
        denominationCountValueWrapper.setDenominationNoteCounts(noteCounts);
        denominationCountValueWrapper.setDenominationNoteValues(noteValues);

        return denominationCountValueWrapper;

    }

}
