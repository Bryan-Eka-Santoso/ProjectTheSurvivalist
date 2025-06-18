package Objek.Items.Unstackable.Arsenals;

import java.io.IOException;
import javax.imageio.ImageIO;

public class WoodenClub extends Club {
    
    public WoodenClub() {
        super("Wooden Club", 15, 60);
        try {
            this.img = ImageIO.read(getClass().getResource("/res/Items/Equipments/woodenclub.png")); 
        } catch (IOException e) {
            e.printStackTrace();
        }  
    }
}
