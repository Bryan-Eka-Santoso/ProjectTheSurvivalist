package Objek.Items.StackableItem.Materials.ForgedComponents;

import Objek.Items.StackableItem.Materials.Material;

public class GoldIngot extends Material {
    public GoldIngot(int currentStack) {
        super("Gold Ingot" , currentStack);
        this.isCanSell = true;
        this.price = 100; // Set the price for Gold Ingot
        try {
            this.img = javax.imageio.ImageIO.read(new java.io.File("ProjectTheSurvivalist/res/Items/Material/goldingot.png")); 
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
    
}
