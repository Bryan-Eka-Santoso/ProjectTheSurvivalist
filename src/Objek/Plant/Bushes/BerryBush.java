package Objek.Plant.Bushes;

import javax.imageio.ImageIO;
import Objek.Controller.GamePanel;

public class BerryBush extends Bush {
    public BerryBush(int x, int y, GamePanel gp) {
        super(x, y, gp);
        try {
            this.image = ImageIO.read(getClass().getResource("/res/plant/bush4c.png"));
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
    
}
