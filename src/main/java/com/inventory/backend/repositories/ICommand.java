package com.inventory.backend.repositories;

import com.inventory.backend.model.Item;

public interface ICommand {

    void create(Item item);

    void updateBuy(String itemName, int quantity);

    void updateSell(String itemName, int quantity);

    void delete(String itemName);

    void report();

}
