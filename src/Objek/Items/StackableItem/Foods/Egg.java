package Objek.Items.StackableItem.Foods;

import Objek.Items.StackableItem.Stackable;

public class Egg extends Stackable {
    public Egg(String name, int maxStack, int currentStack) {
        super(name, maxStack, currentStack);
        try {
            this.img = javax.imageio.ImageIO.read(new java.io.File("ProjectTheSurvivalist/res/Items/Foods/egg.png")); 
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }    
}
