package Objek.Items.Unstackable.Arsenals;

import java.io.IOException;
import javax.imageio.ImageIO;

public class SpikedWoodenClub extends Club {
    
    public SpikedWoodenClub() {
        super("Spiked Wooden Club", 23, 80);
        try {
            this.img = ImageIO.read(getClass().getResource("/res/Items/Equipments/spikedwoodenclub.png")); 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
