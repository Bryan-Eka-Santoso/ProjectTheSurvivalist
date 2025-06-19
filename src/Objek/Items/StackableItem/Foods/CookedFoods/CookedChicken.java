package Objek.Items.StackableItem.Foods.CookedFoods;

import Objek.Items.StackableItem.Foods.Food;

public class CookedChicken extends Food {
    
    public CookedChicken(int currentStack) {
        super("Cooked Chicken", currentStack);
        try {
            this.img = javax.imageio.ImageIO.read(getClass().getResource("/res/Items/Foods/chickendrum.png"));
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
    
}
