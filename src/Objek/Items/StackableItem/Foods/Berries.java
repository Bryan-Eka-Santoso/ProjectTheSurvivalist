package Objek.Items.StackableItem.Foods;// pac

import Objek.Items.StackableItem.Foods.RawFoods.Blackberries;
import Objek.Items.StackableItem.Materials.Material;

public class Berries extends Material {
    public Berries(String name, int currentStack) {
        super(name, currentStack);
    }
    public Berries createBerries(int type, int currentStack) {
        switch (type) {
            case 1:
                return new Blackberries(currentStack);
            // case 2:
            //     return new Blueberries(currentStack);
            // case 3:
            //     return new RaspBerries(currentStack);
            default:
                throw new IllegalArgumentException("Unknown berry type: " + type);
        }
    }     
}
