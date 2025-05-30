package Objek.Ore;

import java.awt.Rectangle;
import java.io.File;

import javax.imageio.ImageIO;

import Objek.Controller.GamePanel;

public class GemOre extends Ore {
    private static int spriteDisplaySize = 45;
    private static int originalSpriteSize = 32;
    private static double scaleFactor = (double)spriteDisplaySize / originalSpriteSize;
    public GemOre(int x, int y, GamePanel gp) {
        super(175, x, y, gp.TILE_SIZE , gp.TILE_SIZE, gp, new Rectangle((int)(1*scaleFactor), (int)(1*scaleFactor), (int)(29*scaleFactor), (int)(28*scaleFactor)));
        this.collision = true;
        try {
            this.image = ImageIO.read(new File("ProjectTheSurvivalist/res/Ore/crystal.png"));
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
