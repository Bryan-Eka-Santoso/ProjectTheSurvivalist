package Objek.Items.StackableItem.Foods.Other;

import Objek.Items.StackableItem.Foods.Food;

public class BakedPotato extends Food {
    
    public BakedPotato(int currentStack) {
        super("Baked Potato", currentStack);
        this.isCanSell = true;
        this.price = 5;
        try {
            this.img = javax.imageio.ImageIO.read(getClass().getResource("/res/Items/Foods/bakedpotato.png"));
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
