package Objek.Ore;

import java.io.File;

import javax.imageio.ImageIO;
import java.awt.Rectangle;
import Objek.Controller.GamePanel;

public class Rock extends Ore{
    private static int spriteDisplaySize = 45;
    private static int originalSpriteSize = 32;
    private static double scaleFactor = (double)spriteDisplaySize / originalSpriteSize;
    public Rock(int x, int y, GamePanel gp) {
        super(100, x, y, gp.TILE_SIZE , gp.TILE_SIZE, gp, new Rectangle((int)(1*scaleFactor), (int)(1*scaleFactor), (int)(29*scaleFactor), (int)(28*scaleFactor)));
        this.collision = true;
        try {
            this.image = ImageIO.read(new File("ProjectTheSurvivalist/res/Ore/rock.png"));
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
