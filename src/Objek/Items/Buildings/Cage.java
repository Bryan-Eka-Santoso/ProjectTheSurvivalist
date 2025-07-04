package Objek.Items.Buildings;

import java.awt.Rectangle;
import java.io.IOException;
import Objek.Animal.TameAnimal;
import javax.imageio.ImageIO;

import Objek.Controller.GamePanel;

public abstract class Cage extends Buildings {

    public Cage(String name,GamePanel gp, int buildingMap) {
        super(name, 3, 1, gp, new Rectangle(11,10,42,40),64,64, buildingMap);
        this.gp = gp;
        this.solidAreaDefaultX = solidArea.x;
        this.solidAreaDefaultY = solidArea.y;
        try {
            this.img = ImageIO.read(getClass().getResource("/res/Items/Buildings/coop.png"));
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
