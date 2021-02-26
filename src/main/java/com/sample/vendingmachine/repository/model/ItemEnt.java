package com.sample.vendingmachine.repository.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ITEM")
@Getter
@Setter
@EqualsAndHashCode(exclude = {"name", "price"})
@NoArgsConstructor
@AllArgsConstructor
public class ItemEnt {
    @Id
    String id;
    @Column
    String name;
    @Column
    int price;

}
