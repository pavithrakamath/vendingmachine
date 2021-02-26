package com.sample.vendingmachine.model;

import com.sample.vendingmachine.model.exceptions.InvalidOperationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

class SlotItemTest {
  Item item;

  @BeforeEach
  void prepare() {
    try {
      item = new Item("n1", 5);
    } catch (InvalidOperationException e) {
      item = null;
    }
  }

  @Test
  void slotItemThrowsException_whenQuantityIsNegative() {
    AtomicReference<SlotItem> slotItem = null;
    assertThrows(InvalidOperationException.class, () -> slotItem.set(new SlotItem(item, -1)));
  }

  @Test
  void slotItem_ThrowsExceptionWhenItemIsNull() {
    AtomicReference<SlotItem> slotItem = null;
    assertThrows(InvalidOperationException.class, () -> slotItem.set(new SlotItem(null, 1)));
  }

  @Test
  void slotItem_SuccessfullwithNonNullItemAndPositiveQuantity() throws InvalidOperationException {
    SlotItem slotItem = new SlotItem(item, 1);
    assertEquals(1, slotItem.getQuantity());
    assertEquals(item.getId(), slotItem.getItemId());
    assertEquals(item.getName(), slotItem.getItemName());
    assertEquals(item.getPrice(), slotItem.getItemPrice());
  }

  @Test
  void reduceBy() throws InvalidOperationException {
    SlotItem slotItem = new SlotItem(item, 1);

    slotItem = slotItem.reduceBy(1);

    assertEquals(0, slotItem.getQuantity());
  }

  @Test
  void reduceBy_throwsExceptionWhhenNegativeParamPassed() throws InvalidOperationException {
    SlotItem slotItem = new SlotItem(item, 1);

    assertThrows(InvalidOperationException.class, () -> slotItem.reduceBy(-1));
  }

  @Test
  void incrementBy() throws InvalidOperationException {
    SlotItem slotItem = new SlotItem(item, 1);

    slotItem = slotItem.incrementBy(1);

    assertEquals(2, slotItem.getQuantity());
  }

  @Test
  void incrementBy_throwsExceptionWhhenNegativeParamPassed() throws InvalidOperationException {
    SlotItem slotItem = new SlotItem(item, 1);

    assertThrows(InvalidOperationException.class, () -> slotItem.incrementBy(-1));
  }

    @Test
    void slotItemsEqual() throws InvalidOperationException {
        SlotItem slotItem = new SlotItem(item, 1);
        SlotItem another = new SlotItem(item, 1);
        assertEquals(slotItem,another);
        assertEquals(slotItem.hashCode(),another.hashCode());
    }

    @Test
    void slotItemsNotEqual() throws InvalidOperationException {
        SlotItem slotItem = new SlotItem(item, 1);
        SlotItem another = new SlotItem(item, 2);
        assertNotEquals(slotItem,another);
    }
}
