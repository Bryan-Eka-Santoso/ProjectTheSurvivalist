package Objek.Items.Unstackable.Arsenals;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Sword extends Arsenal {
    public Sword(String name, int damage, int durability) {
        super(name, damage, durability);
        try {
            this.img = ImageIO.read(new File("ProjectTheSurvivalist/res/Items/Equipments/sword.png")); 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
