package com.discovery.banking.controller;

import com.discovery.banking.entity.Client;
import com.discovery.banking.entity.ClientAccount;
import com.discovery.banking.service.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Slf4j
@Controller
@RequestMapping(value = "/client")
public class ClientController {

    @Autowired
    ClientService clientService;

    @RequestMapping(value = "/displayTransactionalBalances")
    public ResponseEntity<List<ClientAccount>> displayTransactionalBalances(@RequestParam int clientId){
        return ResponseEntity.ok().body(clientService.displayTransactionalBalances(clientId));
    }

    @RequestMapping(value = "/displayConvertedCurrencyAccountBalances")
    public ResponseEntity<List<JSONObject>> displayConvertedCurrencyAccountBalances(@RequestParam int clientId) throws JSONException {
        //todo help
        return ResponseEntity.ok().body(clientService.displayConvertedCurrencyAccountBalances(clientId));
    }

//    @RequestMapping(value = "/withdrawMoneyFromTransactionalAccount")
//    public ResponseEntity<List<JSONObject>> withdrawMoneyFromTransactionalAccount(@RequestParam int clientId, String accountNumber, int atmId) throws JSONException {
//        //todo help
////        return ResponseEntity.ok().body(clientService.withdrawMoneyFromTransactionalAccount(clientId, accountNumber, atmId));
//    }
//


}
