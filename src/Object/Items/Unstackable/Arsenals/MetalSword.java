package Object.Items.Unstackable.Arsenals;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class MetalSword extends Sword {
    public MetalSword() {
        super("Metal Sword", 18, 120);
        try {
            this.img = ImageIO.read(new File("ProjectTheSurvivalist/res/Items/Equipments/metalsword.png")); 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
