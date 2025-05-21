package Objek.Plant;

import Objek.Controller.GamePanel;

public class BerryBush extends Bush {
    public BerryBush(int x, int y, GamePanel gp) {
        super(x, y, gp);
        try {
            this.image = javax.imageio.ImageIO.read(new java.io.File("ProjectTheSurvivalist/res/plant/bush4c.png"));
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
    
}
