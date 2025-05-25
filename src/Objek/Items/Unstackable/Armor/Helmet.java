package Objek.Items.Unstackable.Armor;

import Objek.Items.Unstackable.Unstackable;

public class Helmet extends Unstackable {

    public Helmet(String name, String description, int durability, int defense) {
        super(name);
        this.defense = defense;
    }

    private int defense;

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }
}
