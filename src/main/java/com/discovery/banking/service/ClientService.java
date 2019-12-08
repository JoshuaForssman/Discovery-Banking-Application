package com.discovery.banking.service;

import com.discovery.banking.dao.ClientRepository;
import com.discovery.banking.entity.AccountTypeCodeOptions;
import com.discovery.banking.entity.Atm;
import com.discovery.banking.entity.AtmAllocation;
import com.discovery.banking.entity.Client;
import com.discovery.banking.entity.ClientAccount;
import com.discovery.banking.utils.CurrencyUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
@Service
public class ClientService {

    @Autowired
    ClientRepository clientRepository;

//    @Autowired
//    AtmService atmService;

    public List<ClientAccount> displayTransactionalBalances(int clientId){

        Client currentClient = retrieveClient(clientId);
        List<ClientAccount> clientTransactionalAccounts = getClientTransactionalAccounts(currentClient);

        Comparator<ClientAccount> byDisplayBalance = Comparator.comparing(ClientAccount::getDisplayBalance).reversed();
        Collections.sort(clientTransactionalAccounts,byDisplayBalance);

        return clientTransactionalAccounts;
    }

    public List<JSONObject> displayConvertedCurrencyAccountBalances(int clientId) throws JSONException {

        Client currentClient = retrieveClient(clientId);
        List<ClientAccount> clientCurrencyAccounts = getClientCurrencyAccounts(currentClient);
        List<JSONObject> convertedAccounts = new ArrayList<>();
        JSONObject entity = new JSONObject();
        for (ClientAccount account: clientCurrencyAccounts ) {
            String currencyIndicator = account.getCurrency().getCurrencyConversionRate().getConversionIndicator();
            log.info("Currency code {}" , currencyIndicator);

            BigDecimal rate = account.getCurrency().getCurrencyConversionRate().getRate();
            log.info("Currency rate {}" , rate);

            BigDecimal value = account.getDisplayBalance();
            log.info("Currency value {}" , value);

            int decimalPlaces = account.getCurrency().getDecimalPlaces();
            log.info("Currency decimal places {}" , decimalPlaces);


            entity.put("accountNumber", account.getClientAccountNumber());
            entity.put("currency", account.getCurrency().getCurrencyCode());
            entity.put("currencyBalance", account.getDisplayBalance());
            entity.put("conversionRate", rate);
            entity.put("zarAmount", CurrencyUtil.calculateZar(rate, currencyIndicator,value,decimalPlaces));

            convertedAccounts.add(entity);
        }

//        Comparator<JSONObject> byDisplayBalance = Comparator.comparing(JSONObject::JSONObject.gett("zarAmount")).reversed();
//
//        Comparator<JSONObject> byDisplayBalance = Comparator.comparing(JSONObject::getJSONObject("zarAmount")).reversed();
        return convertedAccounts;
    }

//    public int withdrawMoneyFromTransactionalAccount(int clientId, String accountNumber, int atmId){
//
//        Client currentClient = retrieveClient(clientId);
////        AtmAllocation currentAtm = atmService.retrieveAtm(atmId);
//
////        currentAtm.getDenomination().
//    }


    public Client retrieveClient(int clientId){

        Client currentRequestedClient = clientRepository.getOne(clientId);
        return currentRequestedClient;
    }


    public List<ClientAccount> getClientCurrencyAccounts(Client client){

        List<ClientAccount> clientTransactionalAccounts = new ArrayList<>();

        for (ClientAccount account:client.getClientAccounts()) {
            if(account.getAccountType().getAccountTypeCode().equals(AccountTypeCodeOptions.CFCA.value())){
                clientTransactionalAccounts.add(account);
            }
        }

        return clientTransactionalAccounts;
    }

    public List<ClientAccount> getClientTransactionalAccounts(Client client){

        List<ClientAccount> clientTransactionalAccounts = new ArrayList<>();

        for (ClientAccount account:client.getClientAccounts()) {
            if(account.getAccountType().isTransactional()){
                clientTransactionalAccounts.add(account);
            }
        }

        return clientTransactionalAccounts;
    }

}
