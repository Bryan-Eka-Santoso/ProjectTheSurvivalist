package Objek.Items.Unstackable.Arsenals;

import java.io.IOException;
import javax.imageio.ImageIO;

public class FlimsyAxe extends Axe {

    public FlimsyAxe() {
    super("Flimsy Axe", 10, 80);
        try {
            this.img = ImageIO.read(getClass().getResource("/res/Items/Equipments/flimsyaxe.png")); 
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }
    
}
