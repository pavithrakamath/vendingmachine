package com.sample.vendingmachine.repository;

import com.sample.vendingmachine.repository.model.VendingMachineEnt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendingMachineRepository extends JpaRepository<VendingMachineEnt, String> {}
