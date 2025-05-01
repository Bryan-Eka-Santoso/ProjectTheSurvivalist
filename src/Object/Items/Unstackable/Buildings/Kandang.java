package Object.Items.Unstackable.Buildings;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import Object.GamePanel;

public class Kandang extends Buildings {
    GamePanel gp;
    Rectangle solidArea;
    BufferedImage img;

    public Kandang(String name, int x, int y, GamePanel gp, Rectangle solidArea) {
        super(name, x, y);
    }
    
}
