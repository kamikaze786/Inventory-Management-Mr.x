package com.inventory.backend.model;

import java.io.Serializable;

public class Item implements Serializable {

    /** The Serial Version UID for Serializable classes. */
    private static final long serialVersionUID = 1L;

    /** item name **/
    private String name;

    /** item cost price **/
    private double costPrice;

    /** item sell price **/
    private double sellPrice;

    /** item quantity on stock **/
    private int quantity;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(double costPrice) {
        this.costPrice = costPrice;
    }

    public double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
