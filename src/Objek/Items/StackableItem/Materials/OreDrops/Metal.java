package Objek.Items.StackableItem.Materials.OreDrops;

import Objek.Items.StackableItem.Materials.Material;

public class Metal extends Material {
    public Metal(int currentStack) {
        super("Metal", currentStack);
        try {
            this.img = javax.imageio.ImageIO.read(new java.io.File("ProjectTheSurvivalist/res/Items/Material/metal.png")); 
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

}
