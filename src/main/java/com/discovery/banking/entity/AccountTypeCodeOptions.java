package com.discovery.banking.entity;

public enum AccountTypeCodeOptions {

    CHQ,
    SVGS,
    PLOAN,
    HLOAN,
    CCRD,
    CFCA;

    public String value() {
        return name();
    }

}
