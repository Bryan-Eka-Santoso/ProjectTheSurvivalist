package Objek.Items.Unstackable.Arsenals;

import java.io.IOException;
import javax.imageio.ImageIO;

public class WindAxe extends Axe {
    
    public WindAxe() {
        super("Wind Axe", 20, 150);
        try {
            this.img = ImageIO.read(getClass().getResource("/res/Items/Equipments/windaxe.png")); 
        } catch (IOException e) {
            e.printStackTrace();
        }  
    }
}
