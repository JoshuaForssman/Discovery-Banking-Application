package com.discovery.banking.entity;

import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.List;

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

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "ATM_ID")
    private List<AtmAllocation> atmAllocations;

}