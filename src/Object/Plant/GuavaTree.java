package Object.Plant;

import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Object.Controller.GamePanel;

public class GuavaTree extends Plant {

    
    public GuavaTree (int x, int y, GamePanel gp) {
        super(x, y, gp, new Rectangle(12, 24, 24, 20), true);
        this.hp = 100;
        try {
            this.image = ImageIO.read(new File("ProjectTheSurvivalist/res/plant/tree.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
