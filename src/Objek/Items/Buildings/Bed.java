package Objek.Items.Buildings;

import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import Objek.Controller.GamePanel;

public class Bed extends Buildings {
    public Bed(GamePanel gp, int currentStack, int buildingMap) {
        super("Bed", 10, currentStack, gp, new Rectangle(12, 8, 42, 56), 68, 68, buildingMap);
        try {
            this.img = ImageIO.read(new File("ProjectTheSurvivalist/res/Items/Buildings/bed.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
