package com.sample.vendingmachine.model;

import com.sample.vendingmachine.model.exceptions.InvalidOperationException;

import java.util.UUID;

public class Item extends Entity {

    private int price;
    private String name;

    public Item(String name, int price) throws InvalidOperationException {
        super(UUID.randomUUID().toString());
        if (name == null) {
            throw new InvalidOperationException("cannot create a Null named Item");
        }
        this.name = name;

        if (price <= 0) throw new InvalidOperationException("price should be > 0");
        this.price = price;
    }

    public Item(String id, String name, int price) throws InvalidOperationException {
        super(id);
        if (name == null) {
            throw new InvalidOperationException("cannot create a Null named Item");
        }
        this.name = name;

        if (price <= 0) throw new InvalidOperationException("price should be > 0");
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String getId() {
        return super.getId();
    }

    public String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }
}
