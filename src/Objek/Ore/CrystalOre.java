package Objek.Ore;

import java.awt.Rectangle;
import javax.imageio.ImageIO;
import Objek.Controller.GamePanel;

public class CrystalOre extends Ore {
    
    private static int spriteDisplaySize = 45;
    private static int originalSpriteSize = 32;
    private static double scaleFactor = (double)spriteDisplaySize / originalSpriteSize;
    
    public CrystalOre(int x, int y, GamePanel gp) {
        super(175, x, y, gp.TILE_SIZE , gp.TILE_SIZE, gp, new Rectangle((int)(1*scaleFactor), (int)(1*scaleFactor), (int)(29*scaleFactor), (int)(28*scaleFactor)));
        this.collision = true;
        try {
            this.image = ImageIO.read(getClass().getResource("/res/Ore/crystal.png"));
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
