package Objek.Items.Unstackable.Armor.Chestplate;

import java.io.IOException;
import javax.imageio.ImageIO;

public class MetalChestplate extends Chestplate {

    public MetalChestplate() {
        super("Metal Chestplate", 100, 3);
        try {
            this.img = ImageIO.read(getClass().getResource("/res/Items/Armor/metalchestplate2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
