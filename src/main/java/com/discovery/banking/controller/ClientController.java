package com.discovery.banking.controller;

import com.discovery.banking.service.ClientService;
import com.discovery.banking.wrapper.ClientCurrencyAccountWrapper;
import com.discovery.banking.wrapper.ClientTransactionalAccountWrapper;
import com.discovery.banking.wrapper.DenominationCountValueWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/client")
public class ClientController {

    @Autowired
    ClientService clientService;

    @RequestMapping(value = "/displayTransactionalBalances", method = RequestMethod.GET)
    public ResponseEntity<List<ClientTransactionalAccountWrapper>> displayTransactionalBalances(@RequestParam int clientId){
        return ResponseEntity.ok().body(clientService.displayTransactionalBalances(clientId));
    }

    @RequestMapping(value = "/displayConvertedCurrencyAccountBalances",  method = RequestMethod.GET )
    public ResponseEntity<List<ClientCurrencyAccountWrapper>> displayConvertedCurrencyAccountBalances(@RequestParam int clientId) {
        //todo help
        return ResponseEntity.ok().body(clientService.displayConvertedCurrencyAccountBalances(clientId));
    }

    @RequestMapping(value = "/withdrawMoneyFromTransactionalAccount", method = RequestMethod.POST )
    public ResponseEntity<DenominationCountValueWrapper> withdrawMoneyFromTransactionalAccount(@RequestParam int clientId, @RequestParam String withdrawAccount, @RequestParam int atmId, @RequestParam Double withdrawAmount) {
        //todo help
        return ResponseEntity.ok().body(clientService.withdrawMoneyFromTransactionalAccount(clientId, withdrawAccount, atmId, withdrawAmount));
    }



}
