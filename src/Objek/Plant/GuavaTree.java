package Objek.Plant;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import Objek.Controller.GamePanel;

public class GuavaTree extends Tree {

    public GuavaTree (int x, int y, GamePanel gp) {
        super(x, y, gp);
        this.hp = 100;
        try {
            this.image = ImageIO.read(new File("ProjectTheSurvivalist/res/plant/guavatree.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }   
    }
}
