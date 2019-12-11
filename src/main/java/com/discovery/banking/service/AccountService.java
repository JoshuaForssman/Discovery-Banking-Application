package com.discovery.banking.service;

import com.discovery.banking.entity.AccountTypeCodeOptions;
import com.discovery.banking.entity.Client;
import com.discovery.banking.entity.ClientAccount;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class AccountService {

    public List<ClientAccount> getClientTransactionalAccounts(Client client){

        List<ClientAccount> clientTransactionalAccounts = new ArrayList<>();

        for (ClientAccount account:client.getClientAccounts()) {
            if(account.getAccountType().isTransactional()){
                clientTransactionalAccounts.add(account);
            }
        }

        return clientTransactionalAccounts;
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
