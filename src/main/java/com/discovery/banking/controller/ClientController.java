package com.discovery.banking.controller;

import com.discovery.banking.service.ClientService;
import com.discovery.banking.wrapper.ClientCurrencyAccountWrapper;
import com.discovery.banking.wrapper.ClientTransactionalAccountWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Controller
@RequestMapping(value = "/client")
public class ClientController {

    @Autowired
    ClientService clientService;

    @GetMapping(value = "/displayTransactionalBalances")
    public ResponseEntity<List<ClientTransactionalAccountWrapper>> displayTransactionalBalances(@RequestParam int clientId){
        return ResponseEntity.ok().body(clientService.displayTransactionalBalances(clientId));
    }

    @GetMapping(value = "/displayConvertedCurrencyAccountBalances")
    public ResponseEntity<List<ClientCurrencyAccountWrapper>> displayConvertedCurrencyAccountBalances(@RequestParam int clientId) {
        //todo help
        return ResponseEntity.ok().body(clientService.displayConvertedCurrencyAccountBalances(clientId));
    }

//    @RequestMapping(value = "/withdrawMoneyFromTransactionalAccount")
//    public ResponseEntity<List<String>> withdrawMoneyFromTransactionalAccount(@RequestParam int clientId, String withdrawAccount, int atmId, BigDecimal withdrawAmount)) {
//        //todo help
//        return ResponseEntity.badRequest(clientService.)
//    }



}
