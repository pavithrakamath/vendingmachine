package com.sample.vendingmachine.controller;

import com.sample.vendingmachine.model.Item;
import com.sample.vendingmachine.model.exceptions.InvalidOperationException;
import com.sample.vendingmachine.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController {

    @Autowired
    ItemService itemService;

    @GetMapping("/")
    public ResponseEntity<List<Item>> getAllItems() throws InvalidOperationException {
        List<Item> items = itemService.findAll();
        return new ResponseEntity<>(items, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> getItem(@PathVariable(name = "id") String id) throws InvalidOperationException {
        Item item = itemService.findById(id);
        return new ResponseEntity<>(item, new HttpHeaders(), HttpStatus.OK);
    }
}
