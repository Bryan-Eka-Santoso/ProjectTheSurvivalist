package Objek.Ore;

import javax.imageio.ImageIO;
import Objek.Controller.GamePanel;
import java.awt.Rectangle;

public class MetalOre extends Ore {

    private static int spriteDisplaySize = 45;
    private static int originalSpriteSize = 32;
    private static double scaleFactor = (double)spriteDisplaySize / originalSpriteSize;

    public MetalOre(int x, int y, GamePanel gp) {
        super(80, x, y, gp.TILE_SIZE , gp.TILE_SIZE, gp,new Rectangle((int)(1*scaleFactor), (int)(1*scaleFactor), (int)(29*scaleFactor), (int)(28*scaleFactor)));
        this.collision = true;
        try {
            this.image = ImageIO.read(getClass().getResource("/res/Ore/metal.png"));
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}