package Objek.Items.Unstackable.Arsenals;

import Objek.Items.Unstackable.Unstackable;

public class Arsenal extends Unstackable {
    public int damage;
    public int durability;
    public int maxDurability;

    public Arsenal(String name, int damage, int durability) {
        super(name);
        this.damage = damage;
        this.durability = durability;
        this.maxDurability = durability;
    }
}
