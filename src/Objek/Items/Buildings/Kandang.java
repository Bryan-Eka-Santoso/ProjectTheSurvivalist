package Objek.Items.Buildings;

import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import Objek.Animal.TameAnimal;
import javax.imageio.ImageIO;

import Objek.Controller.GamePanel;

public abstract class Kandang extends Buildings {

    public Kandang(String name,GamePanel gp) {
        super(name, 3, 1, gp, new Rectangle(11,10,42,40),64,64);
        this.gp = gp;
        this.worldX = gp.player.worldX;
        this.worldY = gp.player.worldY;
        this.solidAreaDefaultX = solidArea.x;
        this.solidAreaDefaultY = solidArea.y;
        try {
            this.img = ImageIO.read(new File("ProjectTheSurvivalist/res/Items/Buildings/coop.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public abstract int getCurrentCapacity();
    public abstract int getMaxCapacity();
    public abstract boolean addAnimal(TameAnimal animal);
    
    public String getName() {
        return name;
    }
}
