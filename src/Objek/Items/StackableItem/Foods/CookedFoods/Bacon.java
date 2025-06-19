package Objek.Items.StackableItem.Foods.CookedFoods;

import Objek.Items.StackableItem.Foods.Food;

public class Bacon extends Food {

    public Bacon(int currentStack) {
        super("Bacon", currentStack);
        try {
            this.img = javax.imageio.ImageIO.read(getClass().getResource("/res/Items/Foods/bacon.png"));
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }    
}
