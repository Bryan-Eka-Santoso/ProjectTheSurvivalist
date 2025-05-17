package Objek.Plant;

import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Objek.Controller.GamePanel;

public class Tree extends Plant {
    public Tree(int x, int y, GamePanel gp) {
        super(x, y, gp, new Rectangle(12, 24, 24, 20));
        this.collision = true;
    }
}
