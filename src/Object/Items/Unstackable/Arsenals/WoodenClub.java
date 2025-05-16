package Object.Items.Unstackable.Arsenals;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class WoodenClub extends Club {
    public WoodenClub() {
        super("Wooden Club", 15, 60);
        try {
            this.img = ImageIO.read(new File("ProjectTheSurvivalist/res/Items/Equipments/woodenclub.png")); 
        } catch (IOException e) {
            e.printStackTrace();
        }  
    }
}
