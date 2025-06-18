package Objek.Items.StackableItem.Materials.ForgedComponents;

import Objek.Items.StackableItem.Materials.Material;

public class MetalSheet extends Material {
    
    public MetalSheet(int currentStack) {
        super("Metal Sheet", currentStack);
        try {
            this.img = javax.imageio.ImageIO.read(getClass().getResource("/res/Items/Material/metalsheet.png")); 
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
    
}
