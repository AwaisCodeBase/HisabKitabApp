package com.shera.hisabkitab;

public class Item {
    private String name;
    private double price;
    private String timestamp;

    public Item(String name, double price, String timestamp) {
        this.name = name;
        this.price = price;
        this.timestamp = timestamp;
    }

    public String getName() { return name; }
    public double getPrice() { return price; }
    public String getTimestamp() { return timestamp; }
}
