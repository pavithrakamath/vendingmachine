package com.sample.vendingmachine.repository;

import com.sample.vendingmachine.model.Item;
import com.sample.vendingmachine.repository.model.ItemEnt;
import lombok.SneakyThrows;

public class ItemMapper {
    @SneakyThrows
    public static Item map(ItemEnt itemEnt) {
        return new Item(itemEnt.getId(), itemEnt.getName(), itemEnt.getPrice());
    }

    public static ItemEnt map(Item item) {
        return new ItemEnt(item.getId(), item.getName(), item.getPrice());
    }
}
