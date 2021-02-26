package com.sample.vendingmachine.controller.bo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sample.vendingmachine.model.Item;
import com.sample.vendingmachine.model.SlotItem;
import com.sample.vendingmachine.model.exceptions.InvalidOperationException;
import lombok.ToString;

@ToString(exclude = "item")
public class SlotBO {

  private String id;
  private int quantity;
  private String itemId;
  private String itemName;
  private int position;
  private int itemPrice;

  @JsonIgnore
  private SlotItem item;

  public SlotBO() {
  }

  public SlotBO(
          String id, String itemId, String itemName, int position, int itemPrice, int quantity) {
    this.id = id;
    this.itemId = itemId;
    this.itemName = itemName;
    this.position = position;
    this.quantity = quantity;
    this.itemPrice = itemPrice;
  }

  public SlotBO(String id, int position, SlotItem item) {
    this.id = id;
    this.position = position;
    this.item = item;
    this.itemId = item.getItemId();
    this.itemName = item.getItemName();
    this.itemPrice = item.getItemPrice();
    this.quantity = item.getQuantity();
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getItemId() {
    return itemId;
  }

  public void setItemId(String itemId) {
    this.itemId = itemId;
  }

  public String getItemName() {
    return itemName;
  }

  public void setItemName(String itemName) {
    this.itemName = itemName;
  }

  public int getPosition() {
    return position;
  }

  public void setPosition(int position) {
    this.position = position;
  }

  public int getItemPrice() {
    return itemPrice;
  }

  public void setItemPrice(int itemPrice) {
    this.itemPrice = itemPrice;
  }

  @JsonIgnore
  public SlotItem getItem() {
    try {
      Item itemIn = new Item(itemId, itemName, itemPrice);
      SlotItem slotItem = new SlotItem(itemIn, this.getQuantity());
      return slotItem;
    } catch (InvalidOperationException e) {
      e.printStackTrace();
    }
    return null;
  }

  @JsonIgnore
  public void setItem(SlotItem item) throws InvalidOperationException {
    Item itemIn = new Item(item.getItemId(), item.getItemName(), item.getItemPrice());
    this.item = new SlotItem(itemIn, item.getQuantity());
  }
}
