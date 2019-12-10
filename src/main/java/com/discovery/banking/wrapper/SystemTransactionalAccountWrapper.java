package com.discovery.banking.wrapper;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SystemTransactionalAccountWrapper {

    private Integer clientId;

    private String surname;

    private String clientAccountNumber;

    private String description;

    private BigDecimal displayBalance;

}
