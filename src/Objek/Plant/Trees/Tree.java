package Objek.Plant.Trees;

import java.awt.Rectangle;
import Objek.Controller.GamePanel;
import Objek.Plant.Plant;

public class Tree extends Plant {
    public Tree(int x, int y, GamePanel gp) {
        super(100, x, y, gp.TILE_SIZE * 2, gp.TILE_SIZE * 2, gp, new Rectangle(12, 24, 24, 20));
        this.collision = true;
    }
}
