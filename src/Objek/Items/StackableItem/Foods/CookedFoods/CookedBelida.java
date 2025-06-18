package Objek.Items.StackableItem.Foods.CookedFoods;

import Objek.Items.StackableItem.Foods.Food;

public class CookedBelida extends Food {
    
    public CookedBelida(int currentStack) {
        super("Cooked Belida", currentStack);
        try {
            this.img = javax.imageio.ImageIO.read(getClass().getResource("/res/Items/Foods/cookedBelida.png")); 
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
