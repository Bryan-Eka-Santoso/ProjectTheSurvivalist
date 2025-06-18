package Objek.Items.StackableItem.Foods.CookedFoods;

import Objek.Items.StackableItem.Foods.Food;

public class Steak extends Food {

    public Steak(int currentStack) {
        super("Steak", currentStack);
        try {
            this.img = javax.imageio.ImageIO.read(getClass().getResource("/res/Items/Foods/steak.png")); 
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
