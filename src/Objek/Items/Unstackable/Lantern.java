package Objek.Items.Unstackable;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import Objek.Controller.GamePanel;

public class Lantern extends Unstackable {
    GamePanel gp;
    public int lightRadius;
    public boolean isLit;

    public Lantern(GamePanel gp) {
        super("Lantern");
        this.gp = gp;
        this.lightRadius = 350;
        this.isLit = false; // Torch is lit by default
        try {
            this.img = ImageIO.read(new File("ProjectTheSurvivalist/res/Items/lightItems/lanternoff.png")); 
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }

}
