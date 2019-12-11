package com.discovery.banking.service;

import com.discovery.banking.dao.AtmAllocationRepository;
import com.discovery.banking.dao.AtmRepository;
import com.discovery.banking.entity.Atm;
import com.discovery.banking.entity.AtmAllocation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Slf4j
@Service
public class AtmService {

    @Autowired
    AtmRepository atmRepository;

    @Autowired
    AtmAllocationRepository atmAllocationRepository;

    public List<AtmAllocation> retrieveAtmAllocations(int atmId) {

        List<AtmAllocation> atmAllocations = new ArrayList<>();

        Atm atm = atmRepository.getOne(atmId);
        //retrieves the atm allocations for the specified atm
        atmAllocations = atm.getAtmAllocations();

        return atmAllocations;

    }

    public void atmAllocationDenominationUpdate(LinkedHashMap<BigDecimal,BigDecimal> denominationValues, List<AtmAllocation> atmAllocations){

        for (AtmAllocation allocation: atmAllocations) {
            BigDecimal value = (new ArrayList<BigDecimal>(denominationValues.values())).get(allocation.getCount());
            BigDecimal key = (new ArrayList<BigDecimal>(denominationValues.keySet())).get(allocation.getCount());
            if(allocation.getDenomination().getValue().compareTo(key) == 0){
                allocation.setCount(value.intValue());
                atmAllocationRepository.save(allocation);
            }

        }
    }

}
