package com.sample.vendingmachine.model;

import com.sample.vendingmachine.model.exceptions.InvalidOperationException;
import org.junit.jupiter.api.Test;

import static com.sample.vendingmachine.model.Money.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class VendingMachineTest {

  @Test
  void acceptMoney_AddsMoneyToMoneyFromUser() {
    VendingMachine vm = new VendingMachine();

    vm.acceptMoney(FIVE);

    assertEquals(FIVE, vm.getMoneyFromUser());
  }

  @Test
  void acceptMoney_AddsMoneyToMoneyFromUser_HasCorrectValue() {
    VendingMachine vm = new VendingMachine();

    vm.acceptMoney(FIVE);
    vm.acceptMoney(TEN);

    assertEquals(vm.getMoneyFromUser(), new Money(1, 1, 0));
    assertEquals(vm.getMoneyFromUser().getValue(), 15);
  }

  @Test
  void returnMoney_returnsMoneyFrom() throws InvalidOperationException {
    VendingMachine vm = new VendingMachine();
    vm.acceptMoney(FIVE);
    vm.returnMoney();

    assertEquals(NONE, vm.getMoneyFromUser());
  }

  @Test
  void buyProduct_reducesQuantityAndAddsMoneyToMachine() throws InvalidOperationException {
    VendingMachine vm = new VendingMachine();
    vm.loadSlot(1, new SlotItem(new Item("p1", 10), 10));
    vm.acceptMoney(TEN);
    vm.buyItem(1, 1);

    assertEquals(NONE, vm.getMoneyFromUser());
    assertEquals(TEN, vm.getMoneyInMachine());
    assertEquals(9, vm.getSlotItem(1).getQuantity());
  }

  @Test
  void buyProduct_successWhenDenominationIsDifferent() throws InvalidOperationException {
    VendingMachine vm = new VendingMachine();
    vm.loadSlot(1, new SlotItem(new Item("p1", 10), 10));
    vm.acceptMoney(FIVE);
    vm.acceptMoney(FIVE);

    vm.buyItem(1, 1);

    assertEquals(NONE, vm.getMoneyFromUser());
    assertEquals(new Money(2, 0, 0), vm.getMoneyInMachine());
    assertEquals(9, vm.getSlotItem(1).getQuantity());
  }

  @Test
  void buyProduct_returnCorrectChange() throws InvalidOperationException {
    VendingMachine vm = new VendingMachine();
    vm.loadSlot(1, new SlotItem(new Item("p1", 10), 10));
    vm.acceptMoney(FIVE);
    vm.acceptMoney(FIVE);
    vm.acceptMoney(FIVE);
    vm.acceptMoney(FIVE);

    vm.buyItem(1, 1);

    assertEquals(new Money(2, 0, 0), vm.getMoneyFromUser());
    assertEquals(new Money(2, 0, 0), vm.getMoneyInMachine());
    assertEquals(9, vm.getSlotItem(1).getQuantity());
  }

  @Test
  void buyProduct_returnCorrectChangeAgain() throws InvalidOperationException {
    VendingMachine vm = new VendingMachine();
    vm.loadSlot(1, new SlotItem(new Item("p1", 10), 10));
    vm.acceptMoney(FIVE);
    vm.acceptMoney(TWENTY);
    vm.acceptMoney(TWENTY);
    vm.acceptMoney(TWENTY);
    vm.acceptMoney(TEN);
    vm.acceptMoney(FIVE);

    vm.buyItem(1, 6);

    assertEquals(TWENTY, vm.getMoneyFromUser());
    assertEquals(new Money(2, 1, 2), vm.getMoneyInMachine());
    assertEquals(4, vm.getSlotItem(1).getQuantity());
  }

  @Test
  void buyProduct_FailsWhenMachineNotLoaded() {
    VendingMachine vm = new VendingMachine();
    vm.acceptMoney(TEN);
    assertThrows(InvalidOperationException.class, () -> vm.buyItem(1, 1));
  }

  @Test
  void buyProduct_ReturnsChangeReducesQuantityAndAddsMoneyToMachine()
      throws InvalidOperationException {
    VendingMachine vm = new VendingMachine();
    vm.loadSlot(1, new SlotItem(new Item("p1", 10), 10));
    vm.acceptMoney(TEN);
    vm.acceptMoney(FIVE);

    vm.buyItem(1, 1);

    assertEquals(FIVE, vm.getMoneyFromUser());
    assertEquals(TEN, vm.getMoneyInMachine());
    assertEquals(9, vm.getSlotItem(1).getQuantity());
  }

  @Test
  void buyProductWithEnoughMoney_ThrowsExceptionWhenQuantityIsMoreThanPresentInVM()
      throws InvalidOperationException {
    VendingMachine vm = new VendingMachine();
    vm.loadSlot(1, new SlotItem(new Item("p1", 10), 1));
    vm.acceptMoney(TEN);

    assertThrows(InvalidOperationException.class, () -> vm.buyItem(1, 3));
    assertEquals(TEN, vm.getMoneyFromUser());
    assertEquals(1, vm.getSlotItem(1).getQuantity());
  }

  @Test
  void buyProductWithEnoughQuantity_ThrowsExceptionWhenCostIsMoreThanMoneyFromUser()
      throws InvalidOperationException {
    VendingMachine vm = new VendingMachine();
    vm.loadSlot(1, new SlotItem(new Item("p1", 10), 10));
    vm.acceptMoney(TEN);

    assertThrows(InvalidOperationException.class, () -> vm.buyItem(1, 3));
    assertEquals(TEN, vm.getMoneyFromUser());
    assertEquals(10, vm.getSlotItem(1).getQuantity());
  }
}
