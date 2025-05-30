package Objek.Items.StackableItem.Materials;

import Objek.Items.StackableItem.Stackable;

public class Material extends Stackable {
    public Material(String name, int maxStack, int currentStack) {
        super(name, maxStack, currentStack);
    }

    public Material(String name, int currentStack) {
        super(name, 64, currentStack);
    }
}
