package Objek.Items.Unstackable.Armor.Leggings;

import java.io.IOException;
import javax.imageio.ImageIO;

public class MetalLeggings extends Leggings {

    public MetalLeggings() {
        super("Metal Leggings", 100, 2);
        try {
            this.img = ImageIO.read(getClass().getResource("/res/Items/Armor/metalpants2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
