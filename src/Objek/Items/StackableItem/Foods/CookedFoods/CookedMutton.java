package Objek.Items.StackableItem.Foods.CookedFoods;

import Objek.Items.StackableItem.Foods.Food;

public class CookedMutton extends Food {
    
    public CookedMutton(int currentStack) {
        super("Cooked Mutton", currentStack);
        try {
            this.img = javax.imageio.ImageIO.read(getClass().getResource("/res/Items/Foods/cookedmutton.png"));
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
