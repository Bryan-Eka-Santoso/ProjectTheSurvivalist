package Objek.Plant;

import Objek.Controller.GamePanel;

public class WheatCrops extends Crops {
    public WheatCrops(int x, int y, GamePanel gp) {
        super(x, y, gp);
        try {
            this.image = javax.imageio.ImageIO.read(new java.io.File("ProjectTheSurvivalist/res/Plant/Crops/wheatcrops.png"));
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
    
}
