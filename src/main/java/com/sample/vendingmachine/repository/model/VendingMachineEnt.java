package com.sample.vendingmachine.repository.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Vending_machine")
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"slots", "fiveRupeeCount", "tenRupeeCount", "twentyRupeeCount"})
public class VendingMachineEnt {
  @Id
  private String id;

  @Column(name = "five_rupee_count")
  private int fiveRupeeCount;

  @Column(name = "ten_rupee_count")
  private int tenRupeeCount;

  @Column(name = "twenty_rupee_count")
  private int twentyRupeeCount;

  @OneToMany(
          // mappedBy = "vendingMachine",
          cascade = CascadeType.ALL,
          fetch = FetchType.EAGER)
  @JoinColumn(name = "vending_machine_id")
  private Set<SlotEnt> slots;

  public int getTwentyRupeeCount() {
    return twentyRupeeCount;
  }

  public void setTwentyRupeeCount(int twentyRupeeCount) {
    this.twentyRupeeCount = twentyRupeeCount;
  }

  public int getTenRupeeCount() {
    return tenRupeeCount;
  }

  public void setTenRupeeCount(int tenRupeeCount) {
    this.tenRupeeCount = tenRupeeCount;
  }

  public int getFiveRupeeCount() {
    return fiveRupeeCount;
  }

  public void setFiveRupeeCount(int fiveRupeeCount) {
    this.fiveRupeeCount = fiveRupeeCount;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Set<SlotEnt> getSlots() {
    return slots;
  }

  public void setSlots(Set<SlotEnt> slots) {
    if (this.slots == null) {
      this.slots = new HashSet<>();
    }
    System.out.println("BEFORE " + this.slots.toString());
    this.slots.removeAll(slots);
    System.out.println("REMOVE DONE " + this.slots.toString());
    this.slots.addAll(slots);
    System.out.println("AFTER ADD " + this.slots.toString());

  }
}
