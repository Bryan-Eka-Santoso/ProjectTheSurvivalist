package Objek.Plant;

import Objek.Controller.GamePanel;

public class PalmTree extends Tree {
    public PalmTree(int x, int y, GamePanel gp) {
        super(x, y, gp);
        this.hp = 100;
        try {
            this.image = javax.imageio.ImageIO.read(new java.io.File("ProjectTheSurvivalist/res/plant/palmtree.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
