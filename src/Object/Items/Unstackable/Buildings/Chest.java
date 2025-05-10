package Object.Items.Unstackable.Buildings;

import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import Object.Controller.GamePanel;
import Object.Player.Inventory;

public class Chest extends Buildings {
    Inventory inventory;
    final int maxSize = 15; // Ukuran maksimum inventory chest
    GamePanel gp;

    public Chest(GamePanel gp) {
        super("Chest", gp, new Rectangle(9, 9, 30, 30), 48, 48);
        inventory = new Inventory(maxSize, gp);
        this.gp = gp;
        try {
            this.img = ImageIO.read(new File("ProjectTheSurvivalist/res/Items/Buildings/chest.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }   
}