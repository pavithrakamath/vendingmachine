package com.sample.vendingmachine.model;

import com.sample.vendingmachine.model.exceptions.InvalidOperationException;

import java.util.Objects;

public class SlotItem extends ValueObject {
  private final int quantity;
  private final Item item;

  public SlotItem(Item item, int quantity) throws InvalidOperationException {
    if (item != null) this.item = item;
    else {
      throw new InvalidOperationException("item in the Slot cannot be null");
    }
    if (quantity >= 0) this.quantity = quantity;
    else {
      throw new InvalidOperationException("Quantity cannot be negative");
    }
  }

  public int getQuantity() {
    return quantity;
  }

  public String getItemName() {
    return item.getName();
  }

  public int getItemPrice() {
    return item.getPrice();
  }

  public String getItemId() {
    return item.getId();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    SlotItem slotItem = (SlotItem) o;
    return quantity == slotItem.quantity && item.equals(slotItem.item);
  }

  @Override
  public int hashCode() {
    return Objects.hash(quantity, item.getId());
  }

  public SlotItem reduceBy(int quantity) throws InvalidOperationException {
    if (quantity < 0) throw new InvalidOperationException("quantity cannot be negative");
    return new SlotItem(this.item, this.quantity - quantity);
  }

  public SlotItem incrementBy(int quantity) throws InvalidOperationException {
    if (quantity < 0) throw new InvalidOperationException("quantity cannot be negative");
    return new SlotItem(this.item, this.quantity + quantity);
  }
}
