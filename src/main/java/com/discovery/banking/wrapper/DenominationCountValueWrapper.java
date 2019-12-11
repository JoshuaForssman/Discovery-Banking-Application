package com.discovery.banking.wrapper;

import lombok.Data;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;

@Data
public class DenominationCountValueWrapper {



    private LinkedHashMap<BigDecimal, BigDecimal> noteCountHash;

    private String statusMessage;

}
