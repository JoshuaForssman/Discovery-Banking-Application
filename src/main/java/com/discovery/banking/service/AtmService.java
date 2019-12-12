package com.discovery.banking.service;

import com.discovery.banking.dao.AtmAllocationRepository;
import com.discovery.banking.dao.AtmRepository;
import com.discovery.banking.entity.Atm;
import com.discovery.banking.entity.AtmAllocation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.plaf.PanelUI;
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

    public Atm retrieveAtm(int atmId){
        Atm atm = atmRepository.getOne(atmId);
        return atm;
    }

    public void atmAllocationDenominationCountUpdate(LinkedHashMap<BigDecimal,BigDecimal> denominationValues, Atm atm){


        int counter = 0;
        for (AtmAllocation allocation: atm.getAtmAllocations()) {
            counter = counter++;
            BigDecimal value = (new ArrayList<BigDecimal>(denominationValues.values())).get(counter);
            BigDecimal key = (new ArrayList<BigDecimal>(denominationValues.keySet())).get(counter);
            if(allocation.getDenomination().getValue().compareTo(key) == 0){
                BigDecimal allocationCurrentCount = BigDecimal.valueOf(allocation.getCount());
                allocationCurrentCount = allocationCurrentCount.subtract(value);
                allocation.setCount(allocationCurrentCount.intValue());
                atmRepository.save(atm);
            }

        }
    }

}
