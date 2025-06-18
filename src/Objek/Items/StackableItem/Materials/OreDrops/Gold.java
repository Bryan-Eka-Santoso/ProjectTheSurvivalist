package Objek.Items.StackableItem.Materials.OreDrops;

import Objek.Items.StackableItem.Materials.Material;

public class Gold extends Material {

    public Gold(int currentStack) {
        super("Gold" , currentStack);
        try {
            this.img = javax.imageio.ImageIO.read(getClass().getResource("/res/Items/Material/gold.png")); 
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
