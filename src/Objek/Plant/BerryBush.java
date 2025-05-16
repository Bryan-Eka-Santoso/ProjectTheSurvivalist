package Object.Plant;

import Object.Controller.GamePanel;

public class BerryBush extends Plant {
    public BerryBush(int x, int y, GamePanel gp) {
        super(x, y, gp, new java.awt.Rectangle(12, 24, 24, 20));
        this.hp = 100;
        try {
            this.image = javax.imageio.ImageIO.read(new java.io.File("ProjectTheSurvivalist/res/plant/berrybush.png"));
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
    
}
