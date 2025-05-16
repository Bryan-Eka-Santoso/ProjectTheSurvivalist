package Objek.Items.Unstackable;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class WindAxe extends Axe {
    public WindAxe() {
        super("Wind Axe", 25, 150);
        try {
            this.img = ImageIO.read(new File("ProjectTheSurvivalist/res/Items/Equipments/windaxe.png")); 
        } catch (IOException e) {
            e.printStackTrace();
        }  
    }
}
