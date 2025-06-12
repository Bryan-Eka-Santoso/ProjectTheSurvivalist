package Objek.Items;

import java.awt.image.BufferedImage;

public abstract class Item implements Cloneable {
    public String name;
    public int currentStack;
    public int maxStack;
    public int price;
    public BufferedImage img;
    public boolean isCanSell;
    public boolean isLegendaryItem = false;
    
    public Item(String name, int maxStack, int currentStack, boolean isCanSell, int price) {
        this.name = name;
        this.maxStack = maxStack;
        this.currentStack = currentStack;
        this.isCanSell = isCanSell;
        this.price = price;
    }

    public Item clone() {
        try {
            return (Item) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(); // Seharusnya tidak terjadi
        }
    }
}


