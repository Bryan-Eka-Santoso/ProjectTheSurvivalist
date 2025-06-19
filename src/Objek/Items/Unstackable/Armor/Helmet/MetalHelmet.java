package Objek.Items.Unstackable.Armor.Helmet;

import java.io.IOException;
import javax.imageio.ImageIO;

public class MetalHelmet extends Helmet {

    public MetalHelmet() {
        super("Metal Helmet", 80, 2);
        try {
            this.img = ImageIO.read(getClass().getResource("/res/Items/Armor/metalhelmet2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
