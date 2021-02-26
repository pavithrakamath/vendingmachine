package com.sample.vendingmachine.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sample.vendingmachine.model.exceptions.InvalidOperationException;

import java.util.*;
import java.util.stream.Collectors;

public class VendingMachine extends Entity {

    private final List<Slot> slots;
    private Money moneyInMachine;
    private Money moneyFromUser;

    public VendingMachine() {
        super(UUID.randomUUID().toString());
        moneyInMachine = new Money();
        moneyFromUser = new Money();
        slots = new ArrayList<>();
    }

    public VendingMachine(String id, Money initialMoney) {
        super(id);
        moneyInMachine = initialMoney;
        slots = new ArrayList<>();
    }

    // buy Item
    public void buyItem(int slotPosition, int quantity) throws InvalidOperationException {

        Slot slot = getSlot(slotPosition);
        SlotItem slotItem = slot.getSlotItem();
        if (slotItem == null)
            throw new InvalidOperationException(
                    "No items present in the slot. Load the vending machine first");
        if (slotItem.getQuantity() < quantity)
            throw new InvalidOperationException("Not enough quantity exist in vending machine");

        long cost = slotItem.getItemPrice() * quantity;
        long change = moneyFromUser.getValue() - cost;

        if (change < 0)
            throw new InvalidOperationException(
                    "Money put not enough for the purchase. Add " + change * -1 + " this much more money.");

        moneyFromUser = Money.of(change, moneyInMachine);
        moneyInMachine = moneyInMachine.subtract(moneyFromUser);
        slot.setSlotItem(slotItem.reduceBy(quantity));
    }

  public Money getMoneyFromUser() {
    return moneyFromUser;
  }

  public Money getMoneyInMachine() {
    return new Money(
        moneyInMachine.getFiveRupeeCount(),
        moneyInMachine.getTenRupeeCount(),
        moneyInMachine.getTwentyRupeeCount());
  }

  // acceptMoney
  void acceptMoney(Money money) {
      moneyFromUser = moneyFromUser.add(money);
      moneyInMachine = moneyInMachine.add(money);
  }

    // return money
    void returnMoney() throws InvalidOperationException {
        moneyInMachine = moneyInMachine.subtract(getMoneyFromUser());
        moneyFromUser = Money.NONE;
    }

    public void loadSlot(int slotPosition, SlotItem slotItem) {
        var slot = getSlot(slotPosition);
        slot.setSlotItem(slotItem);
    }

    public SlotItem getSlotItem(int position) {
        Optional<Slot> optionalSlotItem =
                slots.stream().filter(slot -> slot.getPosition() == position).findAny();
        if (optionalSlotItem.isPresent()) return optionalSlotItem.get().getSlotItem();
        else {
            throw new NoSuchElementException();
        }
    }

    @JsonIgnore
    public List<SlotItem> getAllSlotItems() {
        return slots.stream().map(slot -> slot.getSlotItem()).collect(Collectors.toList());
    }

    @JsonIgnore
    public List<Slot> getSlots() {
        return slots;
    }

    private Slot getSlot(int position) {
        Optional<Slot> optionalSlot =
                slots.stream().filter(slot -> slot.getPosition() == position).findAny();
        if (optionalSlot.isPresent()) return optionalSlot.get();
        else {
            var slot = new Slot(this, position);
            slots.add(slot);
            return slot;
        }
    }
}
