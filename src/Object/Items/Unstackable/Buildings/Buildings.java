package Object.Items.Unstackable.Buildings;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import Object.Controller.GamePanel;
import Object.Items.Unstackable.Unstackable;

public class Buildings extends Unstackable {
    public int worldX, worldY, solidAreaX, solidAreaY;
    public GamePanel gp;
    public BufferedImage image;
    public Rectangle solidArea;
    public int solidAreaDefaultX; 
    public int solidAreaDefaultY;
    public boolean collision;
    

    public Buildings(String name) {
        super(name);
    }

}
