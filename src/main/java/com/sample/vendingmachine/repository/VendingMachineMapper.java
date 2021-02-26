package com.sample.vendingmachine.repository;

import com.sample.vendingmachine.model.Money;
import com.sample.vendingmachine.model.VendingMachine;
import com.sample.vendingmachine.repository.model.VendingMachineEnt;

public class VendingMachineMapper {

  public static VendingMachineEnt map(VendingMachine v) {
    VendingMachineEnt ent = new VendingMachineEnt();
    ent.setId(v.getId());
    ent.setFiveRupeeCount(v.getMoneyInMachine().getFiveRupeeCount());
    ent.setTenRupeeCount(v.getMoneyInMachine().getTenRupeeCount());
    ent.setTwentyRupeeCount(v.getMoneyInMachine().getTwentyRupeeCount());
    return ent;
  }

  public static VendingMachine map(VendingMachineEnt v) {
    Money money = getMoney(v);
    VendingMachine ent = new VendingMachine(v.getId(), money);
    return ent;
  }

  private static Money getMoney(VendingMachineEnt vm) {
    return new Money(vm.getFiveRupeeCount(), vm.getTenRupeeCount(), vm.getTwentyRupeeCount());
  }
}
