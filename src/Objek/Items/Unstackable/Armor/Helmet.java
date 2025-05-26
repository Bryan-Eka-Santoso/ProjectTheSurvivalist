package Objek.Items.Unstackable.Armor;

import Objek.Items.Unstackable.Unstackable;

public class Helmet extends Unstackable {

    public int defense;

    public Helmet(String name, String description, int durability, int defense) {
        super(name);
        this.defense = defense;
    }

}
