package Objek.Plant;

import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import Objek.Controller.GamePanel;

public class Bush extends Plant {
    public Bush(int x, int y, GamePanel gp) {
        super(x, y, gp, new Rectangle(12, 24, 24, 20));
        this.hp = 30;
        try {
            this.image = ImageIO.read(new File("ProjectTheSurvivalist/res/plant/bush.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.collision = false;
    }
    
}
