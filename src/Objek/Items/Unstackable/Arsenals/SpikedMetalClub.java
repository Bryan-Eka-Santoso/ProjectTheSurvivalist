package Objek.Items.Unstackable.Arsenals;

import java.io.IOException;
import javax.imageio.ImageIO;

public class SpikedMetalClub extends Club {

    public SpikedMetalClub() {
        super("Spiked Metal Club", 26, 115);
        try {
            this.img = ImageIO.read(getClass().getResource("/res/Items/Equipments/spikedmetalclub.png")); 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
