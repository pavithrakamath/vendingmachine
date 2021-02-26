package com.sample.vendingmachine.controller;

import com.sample.vendingmachine.controller.bo.SlotBO;
import com.sample.vendingmachine.model.Slot;
import com.sample.vendingmachine.model.SlotItem;
import com.sample.vendingmachine.model.VendingMachine;
import com.sample.vendingmachine.model.exceptions.APIError;
import com.sample.vendingmachine.service.VendingMachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/vm")
public class VendingMachineController {
  @Autowired VendingMachineService vendingMachineService;

  @PostMapping
  public ResponseEntity<VendingMachine> createOrUpdateVendingMachine(
      @RequestBody VendingMachine vendingMachine) {
    return new ResponseEntity<>(
        vendingMachineService.createOrUpdateVendingMachine(vendingMachine),
        new HttpHeaders(),
        HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<List<VendingMachine>> getAllVendingMachines() {
    List<VendingMachine> vendingMachines = vendingMachineService.findAll();
    return new ResponseEntity<>(vendingMachines, new HttpHeaders(), HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<VendingMachine> getVendingMachine(@PathVariable(name = "id") String id) {
    VendingMachine vendingMachine = vendingMachineService.findVendingMachine(id);
    return new ResponseEntity<>(vendingMachine, new HttpHeaders(), HttpStatus.OK);
  }

  @PostMapping("/{id}/slot")
  public ResponseEntity<SlotBO> loadSlot(
          @PathVariable(name = "id") String id, @RequestBody SlotBO input) {
    VendingMachine vendingMachine = vendingMachineService.findVendingMachine(id);
    /*Item itemIn = new Item(input.getItemId(), input.getItemName(), input.getItemPrice());
    SlotItem slotItem = new SlotItem(itemIn, input.getQuantity());*/
    SlotItem slotItem = input.getItem();
    vendingMachine.loadSlot(input.getPosition(), slotItem);
    Slot slot = new Slot(input.getId(), vendingMachine, input.getPosition());
    slot.setSlotItem(slotItem);
    slot = vendingMachineService.createOrUpdateSlot(id, slot);
    return new ResponseEntity<>(
            new SlotBO(slot.getId(), slot.getPosition(), slot.getSlotItem()),
            new HttpHeaders(),
            HttpStatus.OK);
  }

  @GetMapping("/{id}/slot")
  public ResponseEntity<List<SlotBO>> getAllSlots(@PathVariable(name = "id") String id) {
    var slots =
            vendingMachineService.findAllSlots(id).stream()
                    .map(slot -> new SlotBO(slot.getId(), slot.getPosition(), slot.getSlotItem()))
                    .collect(Collectors.toList());
    return new ResponseEntity<>(slots, new HttpHeaders(), HttpStatus.OK);
  }

  /* @PostMapping("/{id}/slot")
  public ResponseEntity<SlotBO> createOrUpdateSlot(
      @PathVariable(name = "id") String id, @RequestBody SlotBO input) {
    VendingMachine vm = vendingMachineService.findVendingMachine(id);
    Slot inputSlot = new Slot(input.getId(), vm, input.getPosition());
    inputSlot.setSlotItem(input.getItem());

    var slot = vendingMachineService.createOrUpdateSlot(id, inputSlot);
    var slotBo = new SlotBO(slot.getId(), slot.getPosition(), slot.getSlotItem());
    return new ResponseEntity<>(slotBo, new HttpHeaders(), HttpStatus.OK);
  }*/

  @PostMapping("/{id}/slot/{slotId}")
  public ResponseEntity<SlotBO> purchaseItem(
          @PathVariable(name = "id") String id,
          @PathVariable(name = "id") String slotId,
          @RequestParam int quantity) {
    VendingMachine vm = vendingMachineService.findVendingMachine(id);

    var slot = vendingMachineService.purchase(id, slotId, quantity);
    var slotBo = new SlotBO(slot.getId(), slot.getPosition(), slot.getSlotItem());
    return new ResponseEntity<>(slotBo, new HttpHeaders(), HttpStatus.OK);
  }

  @GetMapping("/{id}/slot/{slotId}")
  public ResponseEntity<SlotBO> getSlot(
          @PathVariable(name = "id") String id, @PathVariable(name = "slotId") String slotId) {
    var slot = vendingMachineService.findSlot(id, slotId);
    var slotBo = new SlotBO(slot.getId(), slot.getPosition(), slot.getSlotItem());
    return new ResponseEntity<>(slotBo, new HttpHeaders(), HttpStatus.OK);
  }

  @ExceptionHandler(EmptyResultDataAccessException.class)
  public ResponseEntity<APIError> NoDataFound(EmptyResultDataAccessException e) {
    APIError apiError = new APIError();
    apiError.setErrorCode(101);
    apiError.setErrorMessage("No Such Data exists in the system");
    apiError.setCause(e.getCause());
    return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
  }
}
