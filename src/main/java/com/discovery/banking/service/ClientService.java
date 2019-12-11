package com.discovery.banking.service;

import com.discovery.banking.dao.ClientAccountRepository;
import com.discovery.banking.dao.ClientRepository;
import com.discovery.banking.entity.AccountTypeCodeOptions;
import com.discovery.banking.entity.Atm;
import com.discovery.banking.entity.AtmAllocation;
import com.discovery.banking.entity.Client;
import com.discovery.banking.entity.ClientAccount;
import com.discovery.banking.entity.Currency;
import com.discovery.banking.entity.Denomination;
import com.discovery.banking.utils.CurrencyUtil;
import com.discovery.banking.wrapper.ClientCurrencyAccountWrapper;
import com.discovery.banking.wrapper.ClientTransactionalAccountWrapper;
import com.discovery.banking.wrapper.DenominationCountValueWrapper;
import com.discovery.banking.wrapper.WithdrawWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.lang.management.BufferPoolMXBean;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

@Slf4j
@Service
public class ClientService {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    ClientAccountRepository clientAccountRepository;

    @Autowired
    AtmService atmService;

    @Autowired
    DenominationService denominationService;

    @Autowired
    AccountService accountService;

    public List<ClientTransactionalAccountWrapper> displayTransactionalBalances(int clientId){

        Client currentClient = retrieveClient(clientId);
        List<ClientAccount> clientTransactionalAccounts = accountService.getClientTransactionalAccounts(currentClient);
        List<ClientTransactionalAccountWrapper> transactionalAccounts = new ArrayList<>();

        for (ClientAccount account: clientTransactionalAccounts) {
            ClientTransactionalAccountWrapper transactionalAccountWrapper = new ClientTransactionalAccountWrapper();

            transactionalAccountWrapper.setAccountNumber(account.getClientAccountNumber());
            transactionalAccountWrapper.setAccountBalance(account.getDisplayBalance());
            transactionalAccountWrapper.setAccountType(account.getAccountType().getDescription());

            transactionalAccounts.add(transactionalAccountWrapper);

        }

        Comparator<ClientTransactionalAccountWrapper> byDisplayBalance = Comparator.comparing(ClientTransactionalAccountWrapper::getAccountBalance).reversed();
        Collections.sort(transactionalAccounts, byDisplayBalance);

        return transactionalAccounts;
    }

    public List<ClientCurrencyAccountWrapper> displayConvertedCurrencyAccountBalances(int clientId) {

        Client currentClient = retrieveClient(clientId);
        List<ClientAccount> clientCurrencyAccounts = accountService.getClientCurrencyAccounts(currentClient);
        List<ClientCurrencyAccountWrapper> convertedAccounts = new ArrayList<>();

        for (ClientAccount account: clientCurrencyAccounts ) {
            String currencyIndicator = account.getCurrency().getCurrencyConversionRate().getConversionIndicator();
            ClientCurrencyAccountWrapper entity = new ClientCurrencyAccountWrapper();
            log.info("Currency code {}" , currencyIndicator);

            BigDecimal rate = account.getCurrency().getCurrencyConversionRate().getRate();
            log.info("Currency rate {}" , rate);

            BigDecimal value = account.getDisplayBalance();
            log.info("Currency value {}" , value);

            int decimalPlaces = account.getCurrency().getDecimalPlaces();
            log.info("Currency decimal places {}" , decimalPlaces);

            entity.setAccountNumber(account.getClientAccountNumber());
            entity.setCurrencyCode(account.getCurrency().getCurrencyCode());
            entity.setCurrencyBalance(account.getDisplayBalance());
            entity.setConversionRate(rate);
            entity.setZarAmount(CurrencyUtil.calculateZar(rate, currencyIndicator,value,decimalPlaces));

            convertedAccounts.add(entity);
        }

        Comparator<ClientCurrencyAccountWrapper> byDisplayBalance = Comparator.comparing(ClientCurrencyAccountWrapper::getZarAmount);
        Collections.sort(convertedAccounts, byDisplayBalance);

        return convertedAccounts;
    }

    public DenominationCountValueWrapper withdrawMoneyFromTransactionalAccount(int clientId, String withdrawAccount, int atmId, BigDecimal withdrawAmount){

        DenominationCountValueWrapper optimalDenominationValues = null;
        try {
            Client currentClient = retrieveClient(clientId);

            List<AtmAllocation> atmAllocations = atmService.retrieveAtmAllocations(atmId);

            if(atmAllocations.equals(null)){
                optimalDenominationValues.setNoteCountHash(null);
                optimalDenominationValues.setStatusMessage("The system displays an error message “ATM not registered or unfunded”");
                return optimalDenominationValues;
            }

            LinkedHashMap<BigDecimal, BigDecimal> noteCountPerValue = denominationService.retrieveNoteAmountPerValue(atmAllocations);

            //retrieves the transactional account used for to withdraw from
            ClientAccount clientAccount = accountService.getTransactionalWithdrawAccount(accountService.getClientTransactionalAccounts(currentClient), withdrawAccount);

            if(withdrawAmount.compareTo(clientAccount.getDisplayBalance()) == 1){
                optimalDenominationValues.setNoteCountHash(null);
                optimalDenominationValues.setStatusMessage("Insufficient funds");
                return optimalDenominationValues;
            }

            optimalDenominationValues = denominationService.determineOptimalDenomination(noteCountPerValue, withdrawAmount);

            atmService.atmAllocationDenominationUpdate(optimalDenominationValues.getNoteCountHash(), atmAllocations);

            clientAccount.setDisplayBalance(clientAccount.getDisplayBalance().subtract(withdrawAmount));

            clientAccountRepository.save(clientAccount);

            return optimalDenominationValues;
        }catch (Exception e){
            log.error(e.getLocalizedMessage());
        }

        return optimalDenominationValues;
    }


    public Client retrieveClient(int clientId){

        Client currentRequestedClient = clientRepository.getOne(clientId);
        return currentRequestedClient;
    }

}
