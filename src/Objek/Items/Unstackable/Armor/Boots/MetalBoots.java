package Objek.Items.Unstackable.Armor.Boots;

import java.io.IOException;
import javax.imageio.ImageIO;

public class MetalBoots extends Boots {
    
    public MetalBoots(){
        super("Metal Boots", 80, 1);
        try {
            this.img = ImageIO.read(getClass().getResource("/res/Items/Armor/metalboots2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
