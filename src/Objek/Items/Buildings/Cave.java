package Objek.Items.Buildings;

import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import Objek.Controller.GamePanel;

public class Cave extends Buildings {
    public Cave(GamePanel gp, int currentStack) {
        super("Cave", 10, currentStack, gp, new Rectangle(0, 0, 96, 64), 96, 64);
        try {
            this.img = ImageIO.read(new File("ProjectTheSurvivalist/res/Items/Buildings/caveopen.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
