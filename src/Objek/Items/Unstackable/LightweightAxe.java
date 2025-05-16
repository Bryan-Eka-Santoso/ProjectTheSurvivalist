package Objek.Items.Unstackable;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class LightweightAxe extends Axe {
    public LightweightAxe() {
        super("Light Weight Axe", 15, 110);
        try {
            this.img = ImageIO.read(new File("ProjectTheSurvivalist/res/Items/Equipments/lightweightaxe.png")); 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
