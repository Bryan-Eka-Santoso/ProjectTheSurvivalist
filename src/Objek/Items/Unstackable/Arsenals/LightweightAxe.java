package Objek.Items.Unstackable.Arsenals;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class LightweightAxe extends Axe {
    public LightweightAxe() {
        super("Lightweight Axe", 15, 110);
        try {
            this.img = ImageIO.read(new File("ProjectTheSurvivalist/res/Items/Equipments/lightweightaxe.png")); 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
