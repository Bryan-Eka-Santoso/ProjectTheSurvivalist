package Objek.Items.StackableItem.Materials.ForgedComponents;

import Objek.Items.StackableItem.Materials.Material;

public class ToolHandle extends Material {

    public ToolHandle(int currentStack) {
        super("Tool Handle", currentStack);
        try {
            this.img = javax.imageio.ImageIO.read(getClass().getResource("/res/Items/Material/toolhandle.png")); 
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
