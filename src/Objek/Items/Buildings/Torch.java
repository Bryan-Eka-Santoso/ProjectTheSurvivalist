package Objek.Items.Buildings;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import Objek.Controller.GamePanel;

public class Torch extends Buildings {
    GamePanel gp;
    public int lightRadius;


    public Torch(GamePanel gp, int currentStack) {
        super("Torch", 10, currentStack, gp, new java.awt.Rectangle(9, 9, 30, 30), 48, 48);
        this.gp = gp;
        this.lightRadius = 350;
        try {
            this.img = ImageIO.read(new File("ProjectTheSurvivalist/res/Items/lightItems/torch1.png")); 
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }

}
