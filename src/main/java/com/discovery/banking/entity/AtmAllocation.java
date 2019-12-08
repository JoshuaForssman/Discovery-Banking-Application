package com.discovery.banking.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "ATM_ALLOCATION")
public class AtmAllocation {

    @Id
    @Column(name = "ATM_ALLOCATION_ID")
    private Integer atmAllocationId;

    @ManyToOne
    @JoinColumn(name = "ATM_ID", nullable = false)
    private Atm atm;

    @ManyToOne
    @JoinColumn(name = "DENOMINATION_ID", nullable = false)
    private Denomination denomination;

    @Column(name = "COUNT")
    private int count;

}