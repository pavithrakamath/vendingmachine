package com.sample.vendingmachine.service;

import com.sample.vendingmachine.model.VendingMachine;
import com.sample.vendingmachine.model.exceptions.InvalidOperationException;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
class VendingMachineServiceTest {

  @Autowired
  VendingMachineService vendingMachineService;

  @Test
  void findAll() {
    List<VendingMachine> machineEntList = vendingMachineService.findAll();
    assertEquals(2, machineEntList.size());

  }

  @Test
  void findSlot() throws InvalidOperationException {
    var slot = vendingMachineService.findSlot("v1", "s1");
    assertEquals(1, slot.getPosition());
    assertEquals(10, slot.getSlotItem().getItemPrice());

  }

}
