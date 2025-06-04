package Objek.Items.StackableItem.Foods.Other;

import Objek.Items.StackableItem.Stackable;

public class Milk extends Stackable{
    public Milk(String name, int maxStack, int currentStack) {
        super(name, maxStack, currentStack);
        try {
            this.img = javax.imageio.ImageIO.read(new java.io.File("ProjectTheSurvivalist/res/Items/Foods/milk.png")); 
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
