package Objek.Items.Unstackable.Armor;
import Objek.Items.Unstackable.Unstackable;

public class Armor extends Unstackable {
    public int defense;
    public int durability;
    public int maxDurability;

    public Armor(String name, int durability, int defense) {
        super(name);
        this.durability = durability;
        this.defense = defense;
        this.maxDurability = durability;
    }
}
