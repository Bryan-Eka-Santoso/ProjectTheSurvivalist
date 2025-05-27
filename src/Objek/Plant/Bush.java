package Objek.Plant;

import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import Objek.Controller.GamePanel;

public class Bush extends Plant {
    public Bush(int x, int y, GamePanel gp) {
        super(20, x, y, gp.TILE_SIZE, gp.TILE_SIZE, gp, new Rectangle(12, 24, 24, 20));
        try {
            this.image = ImageIO.read(new File("ProjectTheSurvivalist/res/plant/bush1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.collision = false;
    }
    
}
