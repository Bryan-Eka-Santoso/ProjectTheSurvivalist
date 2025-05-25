package Objek.Items.Unstackable.Armor;

import Objek.Items.Unstackable.Unstackable;

public class Leggings extends Unstackable {

    private int defense;

    public Leggings(String name, String description, int durability, int defense) {
        super(name);
        this.defense = defense;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }
    
}
