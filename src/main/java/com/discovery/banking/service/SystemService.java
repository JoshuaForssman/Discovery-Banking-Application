package com.discovery.banking.service;

import com.discovery.banking.dao.SystemRepository;
import com.discovery.banking.wrapper.SystemClientNetWrapper;
import com.discovery.banking.wrapper.SystemTransactionalAccountWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class SystemService {

    @Autowired
    SystemRepository systemRepository;

    public  List<SystemTransactionalAccountWrapper> displayTransactionalBalances() {
        return systemRepository.getMaxTransactionalAccountPerClientReport();
    }

    public  List<SystemClientNetWrapper> displayClientNetBalances() {
        return systemRepository.getClientNetReport();

    }

}
