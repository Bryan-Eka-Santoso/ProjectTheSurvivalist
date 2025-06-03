package Objek.Controller;
import Objek.Items.Item;

public class ShopItem {
    public Item item;
    public int price;
    public int category; // 0 = Weapons, 1 = Armor, 2 = Food, etc.
    
    public ShopItem(Item item, int price, int category) {
        this.item = item;
        this.price = price;
        this.category = category;
    }
}