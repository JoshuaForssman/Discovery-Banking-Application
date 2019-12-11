package com.discovery.banking.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.discovery.banking.entity.AtmAllocation;

public interface AtmAllocationRepository extends JpaRepository<AtmAllocation, Integer> {

}
