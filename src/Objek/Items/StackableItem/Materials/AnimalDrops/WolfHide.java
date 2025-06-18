package Objek.Items.StackableItem.Materials.AnimalDrops;

import Objek.Items.StackableItem.Materials.Material;

public class WolfHide extends Material {
    
    public WolfHide(int currentStack) {
        super("Wolf Hide", currentStack);
        this.isCanSell = true;
        this.price = 50;
        try {
            this.img = javax.imageio.ImageIO.read(getClass().getResource("/res/Items/Material/wolfhide.png"));
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
