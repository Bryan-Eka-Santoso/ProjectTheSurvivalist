package Objek.Items.Unstackable.Arsenals;

import java.io.IOException;
import javax.imageio.ImageIO;

public class MetalSword extends Sword {

    public MetalSword() {
        super("Metal Sword", 30, 140);
        try {
            this.img = ImageIO.read(getClass().getResource("/res/Items/Equipments/metalsword.png")); 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
