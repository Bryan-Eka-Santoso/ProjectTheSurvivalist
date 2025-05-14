package Object.Items.Unstackable.Arsenals;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class MetalClub extends Club {
    public MetalClub() {
        super("Metal Club", 18, 85);
        try {
            this.img = ImageIO.read(new File("ProjectTheSurvivalist/res/Items/Equipments/metalclub.png")); 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
