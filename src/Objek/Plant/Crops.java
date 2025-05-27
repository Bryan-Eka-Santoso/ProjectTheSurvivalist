package Objek.Plant;

import java.awt.Rectangle;

import Objek.Controller.GamePanel;

public class Crops extends Plant {
    public Crops(int x, int y, GamePanel gp) {
        super(50, x, y, gp, new Rectangle(12, 24, 24, 20));
    }
    
}
