package com.discovery.banking.service;

import com.discovery.banking.dao.AtmRepository;
import com.discovery.banking.entity.Atm;
import com.discovery.banking.entity.AtmAllocation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class AtmService {

    @Autowired
    AtmRepository atmRepository;

    public List<AtmAllocation> retrieveAtmAllocations(int atmId){

        Atm atm = atmRepository.getOne(atmId);

        //retrieves the atm allocations for the specified atm
        List<AtmAllocation> atmAllocations= atm.getAtmAllocations();

        for (AtmAllocation allocation: atmAllocations) {

        }

        return atmAllocations;
    }

}
