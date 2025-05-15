package Objek.Items.Buildings;

import Objek.Controller.GamePanel;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Smelter extends Buildings {
    public Smelter(GamePanel gp, int currentStack) {
        super("Furnace", 10, currentStack, gp, new Rectangle(9, 9, 30, 30), 48, 48);

        try {
            this.img = ImageIO.read(new File("ProjectTheSurvivalist/res/Items/Buildings/smelter.png"));
        } catch (IOException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
}
