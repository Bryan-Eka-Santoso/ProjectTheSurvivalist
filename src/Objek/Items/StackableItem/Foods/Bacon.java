package Objek.Items.StackableItem.Foods;

import Objek.Items.StackableItem.Stackable;

public class Bacon extends Stackable {
    public Bacon(String name, int maxStack, int currentStack) {
        super(name, maxStack, currentStack);
        try {
            this.img = javax.imageio.ImageIO.read(new java.io.File("ProjectTheSurvivalist/res/Items/Foods/bacon.png")); 
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }    
}
