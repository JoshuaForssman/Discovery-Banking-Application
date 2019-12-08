package com.discovery.banking.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "ATM")
public class Atm {

    @Id
    @Column(name = "ATM_ID")
    private Integer atmId;

    @Column(name = "NAME")
    private String name;

    @Column(name = "LOCATION")
    private String location;

    @OneToOne
    @JoinColumn(name = "ATM_ID")
    private AtmAllocation atmAllocation;

}