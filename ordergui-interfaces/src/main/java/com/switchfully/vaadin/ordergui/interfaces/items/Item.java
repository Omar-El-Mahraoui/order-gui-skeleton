package com.switchfully.vaadin.ordergui.interfaces.items;

public class Item {
    public String id;
    public String name;
    public String description;
    public float price;
    public int amountOfStock;
    public String stockUrgency;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public float getPrice() {
        return price;
    }

    public int getAmountOfStock() {
        return amountOfStock;
    }

    public String getStockUrgency() {
        return stockUrgency;
    }
}
