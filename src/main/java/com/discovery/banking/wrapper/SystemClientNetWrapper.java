package com.discovery.banking.wrapper;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SystemClientNetWrapper {

    private String client;

    private BigDecimal loanBalance;

    private BigDecimal transactionalBalance;

    private BigDecimal netValue;
}
