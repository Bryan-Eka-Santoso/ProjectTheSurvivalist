package Object.Items.Unstackable.Arsenals;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpikedWoodenClub extends Club {
    public SpikedWoodenClub() {
        super("Spiked Wooden Club", 23, 65);
        try {
            this.img = ImageIO.read(new File("ProjectTheSurvivalist/res/Items/Equipments/spikedwoodenclub.png")); 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
