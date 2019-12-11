package com.discovery.banking.controller;

import com.discovery.banking.service.SystemService;
import com.discovery.banking.wrapper.SystemClientNetWrapper;
import com.discovery.banking.wrapper.SystemTransactionalAccountWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/system")
public class SystemController {

    @Autowired
    SystemService systemService;

    @RequestMapping(value = "/displayTransactionalBalancesPerClient", method = RequestMethod.GET)
    public ResponseEntity<List<SystemTransactionalAccountWrapper>> displayTransactionalBalancesPerClient() {
        return ResponseEntity.ok().body(systemService.displayTransactionalBalances());
    }

    @RequestMapping(value = "/displayNetBalancesPerClient", method = RequestMethod.GET)
    public ResponseEntity<List<SystemClientNetWrapper>> displayNetBalancesPerClient() {
        return ResponseEntity.ok().body(systemService.displayClientNetBalances());
    }

}
