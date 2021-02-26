package com.sample.vendingmachine.repository.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "SLOT")
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"vendingMachine"})
public class SlotEnt {
  @Id
  private String id;

  @ManyToOne(fetch = FetchType.LAZY)
  /*@JoinColumn(
      foreignKey = @ForeignKey(name = "vending_machine_id"),
      name = "vending_machine_id",
      nullable = false)*/
  @JsonIgnore
  private VendingMachineEnt vendingMachine;

  @Column(name = "position")
  private int position;

  @Column(name = "quantity")
  private int quantity;

  @ManyToOne(targetEntity = ItemEnt.class, cascade = CascadeType.DETACH)
  @JoinColumn(name = "item_id")
  private ItemEnt item;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public VendingMachineEnt getVendingMachine() {
    return vendingMachine;
  }

  public void setVendingMachine(VendingMachineEnt vendingMachine) {
    this.vendingMachine = vendingMachine;
  }

  public int getPosition() {
    return position;
  }

  public void setPosition(int position) {
    this.position = position;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public ItemEnt getItem() {
    return item;
  }

  public void setItem(ItemEnt item) {
    this.item = item;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    SlotEnt slotEnt = (SlotEnt) o;
    return Objects.equals(id, slotEnt.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
