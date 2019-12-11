package com.discovery.banking.service;

import com.discovery.banking.entity.AtmAllocation;
import com.discovery.banking.entity.Notes;
import com.discovery.banking.wrapper.DenominationCountValueWrapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class DenominationService {


    public LinkedHashMap<BigDecimal, BigDecimal> retrieveNoteAmountPerValue(List<AtmAllocation> atmAllocations){

        LinkedHashMap<BigDecimal, BigDecimal> noteAmoutPerValue = new LinkedHashMap<>();

        for (AtmAllocation allocation: atmAllocations){
            String denominationTypeCode = allocation.getDenomination().getDenominationType().getDenominationTypeCode();
            BigDecimal value = allocation.getDenomination().getValue();
            if(denominationTypeCode.equals(Notes.N.toString())){
                noteAmoutPerValue.put(value, BigDecimal.valueOf(allocation.getCount()));
            }
        }

        return noteAmoutPerValue;
    }

    public static DenominationCountValueWrapper determineOptimalDenomination(LinkedHashMap<BigDecimal, BigDecimal> noteCountPerValue, BigDecimal withdrawAmount){

        DenominationCountValueWrapper denominationCountValueWrapper = new DenominationCountValueWrapper();
        LinkedHashMap<BigDecimal, BigDecimal> denominationValuesHash = new LinkedHashMap<>();
        LinkedHashMap<BigDecimal, BigDecimal> noteCountPerValues = noteCountPerValue;
        BigDecimal maxWithdrawableAmount = BigDecimal.ZERO;
        BigDecimal drawingAmount = withdrawAmount;

        try {
            for (int i = noteCountPerValue.size() - 1; i >= 0; i--) {
                // Find denominations
                BigDecimal noteCount = BigDecimal.ZERO;
                BigDecimal noteValue = (new ArrayList<BigDecimal>(noteCountPerValue.keySet())).get(i);
                denominationValuesHash.putIfAbsent(noteValue, noteCount);

                BigDecimal currentValueNoteCount = (new ArrayList<BigDecimal>(noteCountPerValue.values())).get(i);

                while (drawingAmount.compareTo(noteValue) >= 0) {

                    //if there is no more notes for the specific value
                    if (currentValueNoteCount.compareTo(BigDecimal.ZERO) == 0) {
                        break;
                    }

                    //deducting count for specific note
                    noteCountPerValues.replace(noteValue, currentValueNoteCount.subtract(BigDecimal.valueOf(1)), currentValueNoteCount);

                    //setting the amount that will be used to check that
                    BigDecimal withdrawBreak = drawingAmount;
                    noteCount = noteCount.add(BigDecimal.valueOf(1));

                    //subtracting note count for specific value
                    currentValueNoteCount = currentValueNoteCount.subtract(BigDecimal.valueOf(1));

                    //replacing value count with incremented value
                    denominationValuesHash.replace(noteValue, noteCount.subtract(BigDecimal.valueOf(1)), noteCount);
                    drawingAmount = drawingAmount.subtract(noteValue);

                    //if note amount fails, setting max drawing amount
                    maxWithdrawableAmount = maxWithdrawableAmount.add(noteValue);

                }

            }
        }catch (Exception e){
            e.getLocalizedMessage();
        }

        //checking that full amount is withdrawn
        if(drawingAmount.compareTo(BigDecimal.ZERO) > 0){
            denominationCountValueWrapper.setNoteCountHash(null);
            denominationCountValueWrapper.setStatusMessage("Amount not available, would you like to draw " + withdrawAmount + "” where " + maxWithdrawableAmount + " is the next available amount lower than the requested amount." + maxWithdrawableAmount.toString());
            return denominationCountValueWrapper;
        }
        denominationCountValueWrapper.setNoteCountHash(denominationValuesHash);
        denominationCountValueWrapper.setStatusMessage("Success");
        return denominationCountValueWrapper;

    }

}
