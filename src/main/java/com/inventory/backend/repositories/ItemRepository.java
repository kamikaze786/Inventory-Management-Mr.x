package com.inventory.backend.repositories;

import com.inventory.backend.model.Item;
import com.inventory.exceptions.ItemException;
import com.inventory.util.InventoryUtil;

import java.text.DecimalFormat;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ItemRepository implements ICommand {

    private ItemSingleton itemSingleton = ItemSingleton.getInstance();
    private final String REPORT_TITLE = "\t\t\t INVENTORY REPORT";
    private final String ITEM_NAME_COLUMN_TITLE = "Item Name\t\t";
    private final String BOUGHT_AT_COLUMN_TITLE = "Bought At\t\t";
    private final String SOLD_AT_COLUMN_TITLE = "Sold At\t\t\t";
    private final String AVAILABLE_QTY_COLUMN_TITLE = "AvailableQty\t\t";
    private final String VALUE_COLUMN_TITLE = "Value\t\t";
    private final String COLUMN_TITLE_SEPARATOR = "---------\t\t";
    private final String TOTAL_VALUE_FIELD_TITLE = "Total Value\t\t\t\t\t\t\t\t\t\t\t";
    private final String PROFIT_SINCE_LAST_REPORT_FIELD_TITLE = "Profit since last report\t\t\t\t\t\t\t\t\t";

    public void create(Item item) {
        if (!itemSingleton.currentItemMap.containsKey(item.getName())) {
            itemSingleton.currentItemMap.put(item.getName(), item);
            InventoryUtil.logMessage(ItemRepository.class.getSimpleName(), "info", String.format("Item with name %s created with success.", item.getName()));
        } else {
            throw new ItemException(String.format("Item with name %s already registered in the inventory.", item.getName()));
        }
    }

    public void updateBuy(String itemName, int quantity) {
        if (itemSingleton.currentItemMap.containsKey(itemName)) {
            Item tmpItem = itemSingleton.currentItemMap.get(itemName);
            tmpItem.setQuantity(quantity + tmpItem.getQuantity());
            itemSingleton.currentItemMap.replace(itemName, tmpItem);
            InventoryUtil.logMessage(ItemRepository.class.getSimpleName(), "info", String.format("Item with name %s updated quantity by buy with success.", itemName));
        } else {
            throw new ItemException(String.format("Item with name %s doesn't exists in the inventory.", itemName));
        }
    }

    public void updateSell(String itemName, int quantity) {
        if (itemSingleton.currentItemMap.containsKey(itemName)) {
            Item tmpItem = itemSingleton.currentItemMap.get(itemName);

            if (tmpItem.getQuantity() >= quantity) {
                tmpItem.setQuantity(tmpItem.getQuantity() - quantity);
                itemSingleton.currentItemMap.replace(itemName, tmpItem);
                InventoryUtil.logMessage(ItemRepository.class.getSimpleName(), "info", String.format("Item with name %s updated quantity by sell with success.", itemName));
                itemSingleton.currentProfit += quantity * (tmpItem.getSellPrice() - tmpItem.getCostPrice());
            } else {
                throw new ItemException(String.format("Item with name %s doesn't have enough %d quantity for sell", itemName, quantity));
            }

        } else {
            throw new ItemException(String.format("Item with name %s doesn't exists in the inventory.", itemName));
        }
    }

    public void delete(String itemName) {
        if (itemSingleton.currentItemMap.containsKey(itemName)) {
            itemSingleton.currentItemMap.remove(itemName);
            InventoryUtil.logMessage(ItemRepository.class.getSimpleName(), "info", String.format("Item with name %s deleted with success.", itemName));
        } else {
            throw new ItemException(String.format("Item with name %s doesn't exists in the inventory.", itemName));
        }
    }

    public void report() {
        if (itemSingleton.currentItemMap.isEmpty()) {
            throw new ItemException("No data registered in the inventory!");
        } else {
            if (!itemSingleton.lastItemMap.isEmpty()) {
                // Sort Map by key
                Map sortedLastMap = itemSingleton.lastItemMap.entrySet().stream()
                        .sorted(Map.Entry.comparingByKey())
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                                (oldValue, newValue) -> oldValue, LinkedHashMap::new));
                printItemMap(sortedLastMap, "last");
            }

            // Sort Map by key
            Map sortedCurrentMap = itemSingleton.currentItemMap.entrySet().stream()
                    .sorted(Map.Entry.comparingByKey())
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                            (oldValue, newValue) -> oldValue, LinkedHashMap::new));

            printItemMap(sortedCurrentMap, "current");


            itemSingleton.lastItemMap.clear();
            itemSingleton.lastItemMap.putAll(itemSingleton.currentItemMap);
            itemSingleton.lastProfit = itemSingleton.currentProfit;
            itemSingleton.currentProfit = 0.0;
            itemSingleton.currentTotal = 0.0;

        }
    }

    private void printItemMap(Map map, String mapType) {

        DecimalFormat df2 = new DecimalFormat(".00");

        System.out.println(REPORT_TITLE);
        System.out.print(ITEM_NAME_COLUMN_TITLE);
        System.out.print(BOUGHT_AT_COLUMN_TITLE);
        System.out.print(SOLD_AT_COLUMN_TITLE);
        System.out.print(AVAILABLE_QTY_COLUMN_TITLE);
        System.out.println(VALUE_COLUMN_TITLE);

        System.out.print(COLUMN_TITLE_SEPARATOR);
        System.out.print(COLUMN_TITLE_SEPARATOR);
        System.out.print(COLUMN_TITLE_SEPARATOR);
        System.out.print(COLUMN_TITLE_SEPARATOR);
        System.out.println(COLUMN_TITLE_SEPARATOR);

        map.forEach((itemName, i)-> {
            Item item = (Item) i;
            System.out.print(itemName + "\t\t\t");
            System.out.print(df2.format(item.getCostPrice()) + "\t\t\t");
            System.out.print(df2.format(item.getSellPrice()) + "\t\t\t");
            System.out.print(item.getQuantity() + "\t\t\t");
            System.out.println(df2.format(item.getCostPrice() * item.getQuantity()));
            if (mapType.equalsIgnoreCase("current")) {
                itemSingleton.currentTotal += item.getCostPrice() * item.getQuantity();
            } else if (mapType.equalsIgnoreCase("last")) {
                itemSingleton.lastTotal += item.getCostPrice() * item.getQuantity();
            }
        });

        System.out.println(COLUMN_TITLE_SEPARATOR + COLUMN_TITLE_SEPARATOR + COLUMN_TITLE_SEPARATOR + COLUMN_TITLE_SEPARATOR + COLUMN_TITLE_SEPARATOR);

        if (mapType.equalsIgnoreCase("current")) {
            System.out.println(TOTAL_VALUE_FIELD_TITLE + " " + df2.format(itemSingleton.currentTotal));
            System.out.println(PROFIT_SINCE_LAST_REPORT_FIELD_TITLE  + " " + df2.format(itemSingleton.currentProfit));
        } else if (mapType.equalsIgnoreCase("last")) {
            System.out.println(TOTAL_VALUE_FIELD_TITLE + " " + df2.format(itemSingleton.lastTotal));
            System.out.println(PROFIT_SINCE_LAST_REPORT_FIELD_TITLE  + " " + df2.format(itemSingleton.lastProfit));
        }

        System.out.println("\n");
    }
}
