package Objek.Items.Buildings;

import java.awt.Rectangle;
import java.io.IOException;
import javax.imageio.ImageIO;
import Objek.Controller.GamePanel;
import Objek.Player.Inventory;

public class Chest extends Buildings {
    public Inventory inventory;
    public int maxCapacity = 32;

    public Chest(GamePanel gp, int currentStack, int buildingMap) {
        super("Chest", 5, currentStack, gp, new Rectangle(9, 9, 30, 30), 48, 48, buildingMap);
        this.inventory = new Inventory(32, gp);

        try {
            this.img = ImageIO.read(getClass().getResource("/res/Items/Buildings/chest.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }   

}