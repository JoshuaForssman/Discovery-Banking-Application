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
import org.springframework.stereotype.Service;

import java.lang.management.BufferPoolMXBean;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

    public List<ClientTransactionalAccountWrapper> displayTransactionalBalances(int clientId){

        Client currentClient = retrieveClient(clientId);
        List<ClientAccount> clientTransactionalAccounts = getClientTransactionalAccounts(currentClient);
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
        List<ClientAccount> clientCurrencyAccounts = getClientCurrencyAccounts(currentClient);
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

        Comparator<ClientCurrencyAccountWrapper> byDisplayBalance = Comparator.comparing(ClientCurrencyAccountWrapper::getZarAmount).reversed();
        Collections.sort(convertedAccounts, byDisplayBalance);

        return convertedAccounts;
    }

    public  DenominationCountValueWrapper withdrawMoneyFromTransactionalAccount(int clientId, String withdrawAccount, int atmId, Double withdrawAmount){

        Client currentClient = retrieveClient(clientId);

        List<AtmAllocation> atmAllocations = atmService.retrieveAtmAllocations(atmId);
        List<Integer> denominationValues = denominationService.retrieveNoteDenominationsValues(atmAllocations);
        List<Integer> atmDenominationCount = denominationService.retrieveATMDenominationCount(atmAllocations);

        //retrieves the transactional account used for to withdraw from
        ClientAccount clientAccount = getTransactionalWithdrawAccount(getClientTransactionalAccounts(currentClient), withdrawAccount);

        DenominationCountValueWrapper optimalDenominationValues = CurrencyUtil.determineOptimalDenomination(denominationValues, atmDenominationCount, withdrawAmount);

        clientAccount.setDisplayBalance(clientAccount.getDisplayBalance().subtract(BigDecimal.valueOf(withdrawAmount)));

        clientAccountRepository.save(clientAccount);

        return optimalDenominationValues;
    }


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

    public ClientAccount getTransactionalWithdrawAccount(List<ClientAccount> clientAccounts, String withdrawAccount){

        for (ClientAccount account:clientAccounts) {
            if(withdrawAccount.equals(account.getAccountType().getAccountTypeCode())) {
                return account;
            }
        }
        log.error("Specified account{} was not found", withdrawAccount);
        return null;
    }

}
