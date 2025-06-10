package Objek.Items.StackableItem.Foods.Other;

import Objek.Items.StackableItem.Foods.Food;

public class Carrot extends Food {
    public Carrot(int currentStack) {
        super("Carrot", currentStack);
        this.isCanSell = true;
        this.price = 8; // Set the price for Carrot
        try {
            this.img = javax.imageio.ImageIO.read(new java.io.File("ProjectTheSurvivalist/res/Items/Foods/carrot.png"));
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
    
}
