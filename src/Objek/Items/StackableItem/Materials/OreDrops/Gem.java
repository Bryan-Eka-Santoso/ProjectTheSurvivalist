package Objek.Items.StackableItem.Materials.OreDrops;

import Objek.Items.StackableItem.Materials.Material;

public class Gem extends Material {
    
    public Gem(int currentStack) {
        super("Gem", currentStack);
        this.isCanSell = true;
        this.price = 15;
        try {
            this.img = javax.imageio.ImageIO.read(getClass().getResource("/res/Items/Material/gem.png")); 
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
