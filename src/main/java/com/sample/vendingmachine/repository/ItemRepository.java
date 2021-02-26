package com.sample.vendingmachine.repository;

import com.sample.vendingmachine.repository.model.ItemEnt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<ItemEnt, String> {
}
