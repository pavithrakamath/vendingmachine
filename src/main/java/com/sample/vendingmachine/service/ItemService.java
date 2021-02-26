package com.sample.vendingmachine.service;

import com.sample.vendingmachine.model.Item;
import com.sample.vendingmachine.repository.ItemMapper;
import com.sample.vendingmachine.repository.ItemRepository;
import com.sample.vendingmachine.repository.model.ItemEnt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.sample.vendingmachine.repository.ItemMapper.map;

@Service
public class ItemService {
    @Autowired
    ItemRepository itemRepository;


    public List<Item> findAll() {
        List<ItemEnt> itemEnts = itemRepository.findAll();
        return itemEnts.stream().map(ItemMapper::map).collect(Collectors.toList());
    }

    public Item findById(String id) {
        Optional<ItemEnt> optionalItemEnt = itemRepository.findById(id);
        if (optionalItemEnt.isPresent()) {
            return map(optionalItemEnt.get());
        } else {
            throw new EmptyResultDataAccessException(1);
        }
    }
}
