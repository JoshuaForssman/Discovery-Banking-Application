package com.discovery.banking.wrapper;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ClientTransactionalAccountWrapper {

    private String accountNumber;

    private String accountType;

    private BigDecimal accountBalance;

}
