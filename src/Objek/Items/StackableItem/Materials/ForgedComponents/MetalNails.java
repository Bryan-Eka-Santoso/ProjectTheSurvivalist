package Objek.Items.StackableItem.Materials.ForgedComponents;

import Objek.Items.StackableItem.Materials.Material;

public class MetalNails extends Material {
    public MetalNails(int currentStack) {
        super("Metal Nails", currentStack);
        try {
            this.img = javax.imageio.ImageIO.read(new java.io.File("ProjectTheSurvivalist/res/Items/Material/metalnails.png"));
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
