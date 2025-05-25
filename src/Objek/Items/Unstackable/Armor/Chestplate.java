package Objek.Items.Unstackable.Armor;

import Objek.Items.Unstackable.Unstackable;

public class Chestplate extends Unstackable {

    private int defense;

    public Chestplate(String name, String description, int durability, int defense) {
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
