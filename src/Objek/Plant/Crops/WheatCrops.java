package Objek.Plant.Crops;

import java.io.File;
import javax.imageio.ImageIO;
import Objek.Controller.GamePanel;

public class WheatCrops extends Crops {
    public WheatCrops(int x, int y, GamePanel gp) {
        super(x, y, gp);
        try {
            this.image = ImageIO.read(new File("ProjectTheSurvivalist/res/Plant/Crops/wheatcrops.png"));
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
    
}
