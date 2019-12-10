package com.discovery.banking.controller;

import com.discovery.banking.service.SystemService;
import com.discovery.banking.wrapper.SystemClientNetWrapper;
import com.discovery.banking.wrapper.SystemTransactionalAccountWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequestMapping(value = "/system")
public class SystemController {

    @Autowired
    SystemService systemService;

    @RequestMapping(value = "/displayTransactionalBalancesPerClient")
    public ResponseEntity<List<SystemTransactionalAccountWrapper>> displayTransactionalBalancesPerClient() {
        return ResponseEntity.ok().body(systemService.displayTransactionalBalances());
    }

    @RequestMapping(value = "/displayNetBalancesPerClient")
    public ResponseEntity<List<SystemClientNetWrapper>> displayNetBalancesPerClient() {
        return ResponseEntity.ok().body(systemService.displayClientNetBalances());
    }

}
