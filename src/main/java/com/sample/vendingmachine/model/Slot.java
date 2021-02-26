package com.sample.vendingmachine.model;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class Slot extends Entity {
  private final VendingMachine vendingMachine;
  private final int position;
  private SlotItem slotItem;

  public Slot(VendingMachine vendingMachine, int position) {
    super(UUID.randomUUID().toString());
    this.vendingMachine = vendingMachine;
    this.position = position;
  }

  public Slot(String id, VendingMachine vendingMachine, int position) {
    super(id);
    this.vendingMachine = vendingMachine;
    this.position = position;
  }

}
