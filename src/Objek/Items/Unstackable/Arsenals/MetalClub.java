package Objek.Items.Unstackable.Arsenals;

import java.io.IOException;
import javax.imageio.ImageIO;

public class MetalClub extends Club {

    public MetalClub() {
        super("Metal Club", 18, 95);
        try {
            this.img = ImageIO.read(getClass().getResource("/res/Items/Equipments/metalclub.png")); 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
