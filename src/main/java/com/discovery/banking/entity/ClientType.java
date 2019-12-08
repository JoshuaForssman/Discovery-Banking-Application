package com.discovery.banking.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "CLIENT_TYPE")
public class ClientType {

    @Id
    @Column(name = "CLIENT_TYPE_CODE")
    private String clientTypeCode;

    @Column(name = "DESCRIPTION")
    private String description;

}
