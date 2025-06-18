package Objek.Items.StackableItem.Materials.OreDrops;

import java.io.IOException;
import javax.imageio.ImageIO;
import Objek.Items.StackableItem.Materials.Material;

public class Crystal extends Material {
    
    public Crystal(int currentStack) {
        super("Crystal", currentStack);
        this.isCanSell = true;
        this.price = 15;
        try {
            this.img = ImageIO.read(getClass().getResource("/res/Items/Material/crystal.png")); 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
