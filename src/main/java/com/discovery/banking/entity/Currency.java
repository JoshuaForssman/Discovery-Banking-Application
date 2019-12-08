package com.discovery.banking.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "CURRENCY")
public class Currency implements Serializable {

    @Id
    @Column(name = "CURRENCY_CODE")
    private String currencyCode;

    @Column(name = "DECIMAL_PLACES")
    private int decimalPlaces;

    @Column(name = "DESCRIPTION")
    private String description;

    @OneToOne
    @JoinColumn(name = "CURRENCY_CODE")
    private CurrencyConversionRate currencyConversionRate;

}
