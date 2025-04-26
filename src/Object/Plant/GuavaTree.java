package Object.Plant;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Object.GamePanel;

public class GuavaTree extends Plant {
    
    public GuavaTree (int x, int y, GamePanel gp) {
        super(x, y, gp);
        try {
            this.image = ImageIO.read(new File("res/plant/tree.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
