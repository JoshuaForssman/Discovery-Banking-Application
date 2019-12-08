package com.discovery.banking.dao;

import com.discovery.banking.entity.Atm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AtmRepository extends JpaRepository<Atm, Integer> {
}
