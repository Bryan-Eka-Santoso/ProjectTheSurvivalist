package Objek.Items.StackableItem.Materials.ForgedComponents;

import Objek.Items.StackableItem.Materials.Material;

public class SwordHandle extends Material {
    public SwordHandle(int currentStack) {
        super("Sword Handle", currentStack);
        try {
            this.img = javax.imageio.ImageIO.read(new java.io.File("ProjectTheSurvivalist/res/Items/Material/swordhandle.png")); 
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
    
}
