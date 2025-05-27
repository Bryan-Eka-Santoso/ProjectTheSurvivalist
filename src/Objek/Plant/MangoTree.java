package Objek.Plant;

import java.io.File;
import javax.imageio.ImageIO;
import Objek.Controller.GamePanel;

public class MangoTree extends Tree {
    public MangoTree(int x, int y, GamePanel gp) {
        super(x, y, gp);
        this.hp = 100;
        try {
            this.image = ImageIO.read(new File("ProjectTheSurvivalist/res/plant/mangotree.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
