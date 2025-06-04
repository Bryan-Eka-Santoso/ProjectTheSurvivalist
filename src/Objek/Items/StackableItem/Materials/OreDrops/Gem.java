package Objek.Items.StackableItem.Materials.OreDrops;

import Objek.Items.StackableItem.Materials.Material;

public class Gem extends Material {
    public Gem(int currentStack) {
        super("Gem", currentStack);
        try {
            this.img = javax.imageio.ImageIO.read(new java.io.File("ProjectTheSurvivalist/res/Items/Material/gem.png")); 
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
