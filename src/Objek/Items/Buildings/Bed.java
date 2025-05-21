package Objek.Items.Buildings;

import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import Objek.Controller.GamePanel;

public class Bed extends Buildings {
    public Bed(GamePanel gp, int currentStack) {
        super("Bed", 10, currentStack, gp, new Rectangle(12, 8, 30, 56), 48, 68);
        try {
            this.img = ImageIO.read(new File("ProjectTheSurvivalist/res/Items/Buildings/bed.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
