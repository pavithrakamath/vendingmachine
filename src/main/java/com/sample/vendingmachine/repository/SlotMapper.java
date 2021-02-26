package com.sample.vendingmachine.repository;

import com.sample.vendingmachine.model.Item;
import com.sample.vendingmachine.model.Slot;
import com.sample.vendingmachine.model.SlotItem;
import com.sample.vendingmachine.model.exceptions.InvalidOperationException;
import com.sample.vendingmachine.repository.model.ItemEnt;
import com.sample.vendingmachine.repository.model.SlotEnt;
import lombok.SneakyThrows;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SlotMapper {
    @SneakyThrows
    public static SlotEnt map(Slot slot) {
        SlotItem slotItem = slot.getSlotItem();
        Item item = new Item(slotItem.getItemId(), slotItem.getItemName(), slotItem.getItemPrice());
        ItemEnt itemEnt = ItemMapper.map(item);
        SlotEnt slotEnt =
                new SlotEnt(
                        slot.getId(),
                        VendingMachineMapper.map(slot.getVendingMachine()),
                        slot.getPosition(),
                        slotItem.getQuantity(),
                        itemEnt);

        return slotEnt;
    }

    @SneakyThrows
    public static Set<SlotEnt> map(List<Slot> slots) {
        return slots.stream()
                .map(
                        slot -> {
                            SlotItem slotItem = slot.getSlotItem();
                            Item item = null;
                            try {
                                item =
                                        new Item(slotItem.getItemId(), slotItem.getItemName(), slotItem.getItemPrice());
                            } catch (InvalidOperationException e) {
                                e.printStackTrace();
                            }
                            ItemEnt itemEnt = ItemMapper.map(item);
                            return new SlotEnt(
                                    slot.getId(),
                                    VendingMachineMapper.map(slot.getVendingMachine()),
                                    slot.getPosition(),
                                    slotItem.getQuantity(),
                                    itemEnt);
                        })
                .collect(Collectors.toSet());
    }

    @SneakyThrows
    public static Slot map(SlotEnt slotEnt) {

        Item item = ItemMapper.map(slotEnt.getItem());
        SlotItem slotItem = new SlotItem(item, slotEnt.getQuantity());
        Slot slot =
                new Slot(
                        slotEnt.getId(),
                        VendingMachineMapper.map(slotEnt.getVendingMachine()),
                        slotEnt.getPosition());
        slot.setSlotItem(slotItem);
        return slot;
    }
}
