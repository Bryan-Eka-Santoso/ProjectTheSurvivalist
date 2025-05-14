package Object.Items.Unstackable.Arsenals;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class FlimsyAxe extends Axe {
    public FlimsyAxe() {
    super("Flimsy Axe", 10, 60);
        try {
            this.img = ImageIO.read(new File("ProjectTheSurvivalist/res/Items/Equipments/flimsyaxe.png")); 
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }
    
}
