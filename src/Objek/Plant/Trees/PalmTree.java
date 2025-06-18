package Objek.Plant.Trees;

import javax.imageio.ImageIO;
import Objek.Controller.GamePanel;

public class PalmTree extends Tree {
    public PalmTree(int x, int y, GamePanel gp) {
        super(x, y, gp);
        this.hp = 100;
        try {
            this.image = ImageIO.read(getClass().getResource("/res/plant/palmtree.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
