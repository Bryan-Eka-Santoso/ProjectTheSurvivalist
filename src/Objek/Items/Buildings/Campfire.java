package Objek.Items.Buildings;

import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import Objek.Controller.GamePanel;

public class Campfire extends Buildings {
    public Campfire(GamePanel gp, int currentStack) {
        super("Campfire", 10, currentStack, gp, new Rectangle(9, 9, 30, 30), 48, 48);
        try {
            this.img = ImageIO.read(new File("ProjectTheSurvivalist/res/Items/Buildings/campfire.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
