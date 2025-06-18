package Objek.Items.StackableItem.Foods.Other;

import Objek.Items.StackableItem.Stackable;

public class Egg extends Stackable {
    
    public Egg(int currentStack) {
        super("Egg", currentStack);
        this.isCanSell = true;
        this.price = 10; // Set the price for Egg
        try {
            this.img = javax.imageio.ImageIO.read(getClass().getResource("/res/Items/Foods/egg.png"));
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }    
}
