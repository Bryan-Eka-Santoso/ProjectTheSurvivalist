package Objek.Items.Unstackable.Armor.Boots;

import java.io.IOException;
import javax.imageio.ImageIO;

public class GoldBoots extends Boots {
    
    public GoldBoots() {
        super("Gold Boots", 50, 2);
        try {
            this.img = ImageIO.read(getClass().getResource("/res/Items/Armor/goldboots2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
