package Objek.Items.StackableItem;
import Objek.Items.Item;

public abstract class Stackable extends Item {
    public Stackable(String name, int maxStack, int currentStack) {
        super(name, maxStack, currentStack, false, 0);
    }

    public Stackable(String name, int currentStack) {
        super(name, 64, currentStack, false, 0);
    }
}