package Object.Items.Unstackable.Arsenals;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class LightweightAxe extends Axe {
    public LightweightAxe() {
        super("Light Weight Axe", 15, 80);
        try {
            this.img = ImageIO.read(new File("ProjectTheSurvivalist/res/Items/Equipments/lightweightaxe.png")); 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
