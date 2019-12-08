package com.discovery.banking.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "DENOMINATION_TYPE")
public class DenominationType {

    @Id
    @Column(name = "DENOMINATION_TYPE_CODE ")
    private String denominationTypeCode;

    @Column(name = "DESCRIPTION")
    private String description;

}
