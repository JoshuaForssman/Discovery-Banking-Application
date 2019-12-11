package com.discovery.banking.controller;

import com.discovery.banking.service.ClientService;
import com.discovery.banking.wrapper.ClientCurrencyAccountWrapper;
import com.discovery.banking.wrapper.ClientTransactionalAccountWrapper;
import com.discovery.banking.wrapper.DenominationCountValueWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/client")
public class ClientController {

    @Autowired
    ClientService clientService;

    @RequestMapping(value = "/displayTransactionalBalances", method = RequestMethod.GET)
    public ResponseEntity<List<ClientTransactionalAccountWrapper>> displayTransactionalBalances(@RequestParam int clientId){
        try {
            return ResponseEntity.ok().body(clientService.displayTransactionalBalances(clientId));
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/displayConvertedCurrencyAccountBalances",  method = RequestMethod.GET )
    public ResponseEntity<List<ClientCurrencyAccountWrapper>> displayConvertedCurrencyAccountBalances(@RequestParam int clientId) {
        try {
            return ResponseEntity.ok().body(clientService.displayConvertedCurrencyAccountBalances(clientId));
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/withdrawMoneyFromTransactionalAccount", method = RequestMethod.POST )
    public ResponseEntity<DenominationCountValueWrapper> withdrawMoneyFromTransactionalAccount(@RequestParam int clientId, @RequestParam String withdrawAccount, @RequestParam int atmId, @RequestParam BigDecimal withdrawAmount) {
        //todo help
        try {
            return ResponseEntity.ok().body(clientService.withdrawMoneyFromTransactionalAccount(clientId, withdrawAccount, atmId, withdrawAmount));
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



}
