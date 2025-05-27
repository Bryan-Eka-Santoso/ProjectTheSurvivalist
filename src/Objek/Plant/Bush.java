package Objek.Plant;

import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import Objek.Controller.GamePanel;

public class Bush extends Plant {
    Random rand = new Random();
    public Bush(int x, int y, GamePanel gp) {
        super(20, x, y, gp.TILE_SIZE, gp.TILE_SIZE, gp, new Rectangle(12, 24, 24, 20));
        try {
            this.image = rand.nextInt(6) == 0 ? ImageIO.read(new File("ProjectTheSurvivalist/res/plant/bush1.png")) :
                rand.nextInt(6) == 1 ? ImageIO.read(new File("ProjectTheSurvivalist/res/plant/bush2.png")) :
                rand.nextInt(6) == 2 ? ImageIO.read(new File("ProjectTheSurvivalist/res/plant/bush3.png")) :
                rand.nextInt(6) == 3 ? ImageIO.read(new File("ProjectTheSurvivalist/res/plant/bush4.png")) :
                rand.nextInt(6) == 4 ? ImageIO.read(new File("ProjectTheSurvivalist/res/plant/bush5.png")) :
                ImageIO.read(new File("ProjectTheSurvivalist/res/plant/bush6.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.collision = false;
    }
    
}
