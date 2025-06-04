package Objek.Items.StackableItem.Foods.CookedFoods;

import Objek.Items.StackableItem.Foods.Food;

public class CookedArwana extends Food {
    public CookedArwana(int currentStack) {
        super("Cooked Arwana", currentStack);
        try {
            this.img = javax.imageio.ImageIO.read(new java.io.File("ProjectTheSurvivalist/res/Items/Foods/cookedArwana.png")); 
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
