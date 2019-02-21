package com.inventory.backend.service;

import com.inventory.backend.model.Item;
import com.inventory.backend.repositories.ICommand;
import com.inventory.backend.repositories.ItemRepository;
import com.inventory.exceptions.ItemException;

public class ItemService {
    private static ICommand commandItem = new ItemRepository();

    public static void create(String command) {
        String params[] = command.split(" ");

        if (params.length == 4) {
            Double costPrice = Double.valueOf(params[2].trim());
            Double sellPrice = Double.valueOf(params[3].trim());

            Item item = new Item();
            item.setName(params[1].trim());
            item.setCostPrice(costPrice);
            item.setSellPrice(sellPrice);
            item.setQuantity(0);
            commandItem.create(item);
        } else {
            throw new ItemException("Invalid params for create command!");
        }
    }

    public static void updateBuy(String command) {
        String params[] = command.split(" ");
        if (params.length == 3) {
            commandItem.updateBuy(params[1], Integer.parseInt(params[2]));
        }  else {
            throw new ItemException("Invalid params for updateBuy command!");
        }
    }

    public static void updateSell(String command) {
        String params[] = command.split(" ");
        if (params.length == 3) {
            commandItem.updateSell(params[1], Integer.parseInt(params[2]));
        }  else {
            throw new ItemException("Invalid params for updateSell command!");
        }
    }

    public static void delete(String command) {
        String params[] = command.split(" ");
        commandItem.delete(params[1]);
    }

    public static void report() {
        commandItem.report();
    }

}
