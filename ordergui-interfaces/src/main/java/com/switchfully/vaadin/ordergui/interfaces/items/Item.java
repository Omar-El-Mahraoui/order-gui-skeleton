package com.switchfully.vaadin.ordergui.interfaces.items;

public class Item {
    private String id;
    private String name;
    private String description;
    private Float price;
    private Integer amountOfStock;
    private String stockUrgency;

    public Item() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getAmountOfStock() {
        return amountOfStock;
    }

    public void setAmountOfStock(Integer amountOfStock) {
        this.amountOfStock = amountOfStock;
    }

    public String getStockUrgency() {
        return stockUrgency;
    }

    public void setStockUrgency(String stockUrgency) {
        this.stockUrgency = stockUrgency;
    }
}
