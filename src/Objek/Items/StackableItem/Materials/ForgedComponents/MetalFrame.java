package Objek.Items.StackableItem.Materials.ForgedComponents;

import Objek.Items.StackableItem.Materials.Material;

public class MetalFrame extends Material {
    public MetalFrame(int currentStack) {
        super("Metal Frame", currentStack);
        try {
            this.img = javax.imageio.ImageIO.read(new java.io.File("ProjectTheSurvivalist/res/Items/Material/metalframe.png")); 
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
    
}
