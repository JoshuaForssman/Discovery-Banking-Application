package com.discovery.banking.service;

import com.discovery.banking.entity.AtmAllocation;
import com.discovery.banking.entity.Notes;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class DenominationService {

    public List<Integer> retrieveNoteDenominationsValues(List<AtmAllocation> atmAllocations){

        List<Integer> denominationValues = new ArrayList<>();
        for (AtmAllocation allocation: atmAllocations) {
            String denominationTypeCode = allocation.getDenomination().getDenominationType().getDenominationTypeCode();
            int value = allocation.getDenomination().getValue().intValue();
            if(denominationTypeCode.equals(Notes.N.toString())) {
                denominationValues.add(value);
            }
        }

        return denominationValues;
    }

    public List<Integer> retrieveATMDenominationCount(List<AtmAllocation> atmAllocations){

        List<Integer> atmDenominationCount = new ArrayList<>();
        for (AtmAllocation allocation: atmAllocations) {
            atmDenominationCount.add(allocation.getCount());
        }

        return atmDenominationCount;
    }

}
