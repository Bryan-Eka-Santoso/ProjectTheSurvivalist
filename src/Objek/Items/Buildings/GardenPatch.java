package Objek.Items.Buildings;

import java.awt.image.BufferedImage;

import Objek.Controller.GamePanel;
import Objek.Items.StackableItem.Seeds.Seeds;
import Objek.Plant.Crops;

public class GardenPatch extends Buildings {
    public Seeds seed;
    public Crops crops;
    public BufferedImage cropImg;
    public String phase;

    
    public GardenPatch(GamePanel gp, int currentStack) {
        super("Garden Patch", 15, currentStack, gp, new java.awt.Rectangle(9, 9, 30, 30), 48, 48);
        try {
            this.img = javax.imageio.ImageIO.read(new java.io.File("ProjectTheSurvivalist/res/Items/Buildings/gardenpatch.png")); 
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
