package Objek.Items.Buildings;

import java.awt.Rectangle;
import java.io.IOException;
import javax.imageio.ImageIO;
import Objek.Controller.GamePanel;

public class Cave extends Buildings {

    public Cave(GamePanel gp, int currentStack, int buildingMap) {
        super("Cave", 10, currentStack, gp, new Rectangle(0, 0, 96, 64), 96, 64, buildingMap);
        isBreakable = false;
        try {
            this.img = ImageIO.read(getClass().getResource("/res/Items/Buildings/caveopen.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
