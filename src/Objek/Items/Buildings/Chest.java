package Objek.Items.Buildings;

import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import Objek.Controller.GamePanel;
import Objek.Player.Inventory;

public class Chest extends Buildings {
    public Inventory inventory;
    final int maxSize = 32; // Ukuran maksimum inventory chest
    GamePanel gp;

    public Chest(GamePanel gp, int currentStack) {
        super("Chest", 5, currentStack, gp, new Rectangle(9, 9, 30, 30), 48, 48);
        inventory = new Inventory(maxSize, gp);
        this.gp = gp;
        inventory.addItems(new Torch(gp, 1));
        try {
            this.img = ImageIO.read(new File("ProjectTheSurvivalist/res/Items/Buildings/chest.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }   

}