package Objek.Items.Buildings;

import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import Objek.Controller.GamePanel;

public class Shop extends Buildings {
    public Shop(GamePanel gp, int currentStack) {
        super("Shop", 10, currentStack, gp, new Rectangle(12, 8, 42, 56), 68, 68);
        try {
            this.img = ImageIO.read(new File("ProjectTheSurvivalist/res/Items/Buildings/shop.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
