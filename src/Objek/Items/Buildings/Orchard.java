package Objek.Items.Buildings;

import Objek.Controller.GamePanel;
import Objek.Plant.Tree;

public class Orchard extends Buildings {
    public Tree tree;
    
    public Orchard(GamePanel gp, int currentStack) {
        super("Orchard", 15, currentStack, gp, new java.awt.Rectangle(9, 9, 30, 30), 48, 48);
        try {
            this.img = javax.imageio.ImageIO.read(new java.io.File("ProjectTheSurvivalist/res/Items/Buildings/orchard.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.tree = null;
        this.hp = 50;
    }

    
}
