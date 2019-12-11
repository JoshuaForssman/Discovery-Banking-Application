package com.discovery.banking.wrapper;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ClientCurrencyAccountWrapper {

    private String accountNumber;

    private String currencyCode;

    private BigDecimal currencyBalance;

    private BigDecimal conversionRate;

    private BigDecimal zarAmount;

    private String statusMessage;

}
