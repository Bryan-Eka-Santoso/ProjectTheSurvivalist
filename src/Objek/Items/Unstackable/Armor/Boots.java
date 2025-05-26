package Objek.Items.Unstackable.Armor;

import Objek.Items.Unstackable.Unstackable;

public class Boots extends Unstackable {

    public int defense;

    public Boots(String name, String description, int durability, int defense) {
        super(name);
        this.defense = defense;
    }
}
