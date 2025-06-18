package Objek.Items.StackableItem.Materials.OreDrops;

import javax.imageio.ImageIO;
import Objek.Items.StackableItem.Materials.Material;

public class Stone extends Material {

    public Stone(int currentStack) {
        super("Stone", currentStack);
        try {
            this.img = ImageIO.read(getClass().getResource("/res/Items/Material/stone.png")); 
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
