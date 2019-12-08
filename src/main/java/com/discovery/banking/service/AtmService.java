package com.discovery.banking.service;

import com.discovery.banking.dao.AtmRepository;
import com.discovery.banking.entity.Atm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AtmService {

    @Autowired
    AtmRepository atmRepository;

    public Atm retrieveAtm(int atmId){

        Atm currentRequestedAtm = atmRepository.getOne(atmId);
        return currentRequestedAtm;
    }

}
