package com.sample.vendingmachine.service;

import com.sample.vendingmachine.model.Slot;
import com.sample.vendingmachine.model.VendingMachine;
import com.sample.vendingmachine.repository.SlotMapper;
import com.sample.vendingmachine.repository.VendingMachineRepository;
import com.sample.vendingmachine.repository.model.SlotEnt;
import com.sample.vendingmachine.repository.model.VendingMachineEnt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.sample.vendingmachine.repository.VendingMachineMapper.map;

@Service
public class VendingMachineService {

  @Autowired VendingMachineRepository vendingMachineRepository;

  public List<VendingMachine> findAll() {
    List<VendingMachineEnt> vendingMachineEnts = vendingMachineRepository.findAll();
    List<VendingMachine> vendingMachines = new ArrayList<>();
    for (VendingMachineEnt vm : vendingMachineEnts) {
      vendingMachines.add(map(vm));
    }
    return vendingMachines;
  }

  public VendingMachine createOrUpdateVendingMachine(VendingMachine vendingMachine) {
    VendingMachineEnt vendingMachineEntToSave = map(vendingMachine);

    Optional<VendingMachineEnt> vendingMachineEnt =
            vendingMachineRepository.findById(vendingMachine.getId());
    if (vendingMachineEnt.isPresent()) {
      vendingMachineEntToSave.setSlots(vendingMachineEnt.get().getSlots());
    }
    vendingMachineEntToSave = vendingMachineRepository.save(vendingMachineEntToSave);
    return map(vendingMachineEntToSave);
  }

  public Slot createOrUpdateSlot(String id, Slot slot) {
    Optional<VendingMachineEnt> vendingMachineEnt = vendingMachineRepository.findById(id);
    if (vendingMachineEnt.isPresent()) {
      VendingMachineEnt vendingMachineEntToUpd = vendingMachineEnt.get();
      var slots = vendingMachineEntToUpd.getSlots();
      var inputSlot = SlotMapper.map(slot);
      slots.remove(inputSlot);
      slots.add(inputSlot);
      vendingMachineEntToUpd = vendingMachineRepository.saveAndFlush(vendingMachineEntToUpd);
      var returnData =
              vendingMachineEntToUpd.getSlots().stream()
                      .filter(slotEnt -> slot.getPosition() == slotEnt.getPosition())
                      .findAny();
      if (returnData.isPresent()) {
        return SlotMapper.map(returnData.get());
      } else throw new EmptyResultDataAccessException(1);
    } else throw new EmptyResultDataAccessException(1);
  }

  public Slot purchase(String vid, String sid, int quantity) {
    /*  Optional<VendingMachineEnt> vendingMachineEnt = vendingMachineRepository.findById(vid);
    if (vendingMachineEnt.isPresent()) {
      VendingMachineEnt vendingMachineEntToUpd = vendingMachineEnt.get();
      */
    var vendingMachine = findVendingMachine(vid);
    var slotPosition =
            vendingMachine.getSlots().stream()
                    .filter(slotEnt -> slotEnt.getId().equals(sid))
                    .findAny()
                    .map(slot -> slot.getPosition())
                    .get();
    try {
      vendingMachine.buyItem(slotPosition, quantity);
    } catch (Exception e) {
      throw new EmptyResultDataAccessException(1);
    }

    VendingMachineEnt vendingMachineEntToUpd;
    vendingMachineEntToUpd = map(vendingMachine);
    vendingMachineEntToUpd.setSlots(SlotMapper.map(vendingMachine.getSlots()));
    vendingMachineEntToUpd = vendingMachineRepository.saveAndFlush(vendingMachineEntToUpd);
    var returnData =
            vendingMachineEntToUpd.getSlots().stream()
                    .filter(slotEnt -> slotPosition == slotEnt.getPosition())
                    .findAny();
    if (returnData.isPresent()) {
      return SlotMapper.map(returnData.get());
    } else throw new EmptyResultDataAccessException(1);
  }

  public VendingMachine findVendingMachine(String id) {
    return map(findVendingMachineEnt(id));
  }

  public List<Slot> findAllSlots(String id) {
    var vendingMachineEnt = findVendingMachineEnt(id);
    var slots = vendingMachineEnt.getSlots();
    return slots.stream().map(SlotMapper::map).collect(Collectors.toList());
  }

  public Slot findSlot(String vid, String slotId) {
    var vendingMachineEnt = findVendingMachineEnt(vid);
    Optional<SlotEnt> optionalSlotEnt =
            vendingMachineEnt.getSlots().stream()
                    .filter(slotEnt -> slotId.equals(slotEnt.getId()))
                    .findAny();
    if (optionalSlotEnt.isPresent()) {
      return SlotMapper.map(optionalSlotEnt.get());
    } else throw new EmptyResultDataAccessException(1);
  }

  // Private methods
  private VendingMachineEnt findVendingMachineEnt(String id) {
    Optional<VendingMachineEnt> vendingMachineEnt = vendingMachineRepository.findById(id);
    if (vendingMachineEnt.isPresent()) {
      return vendingMachineEnt.get();
    } else throw new EmptyResultDataAccessException(1);
  }
}
