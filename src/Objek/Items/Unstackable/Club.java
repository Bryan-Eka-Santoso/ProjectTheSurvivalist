package Objek.Items.Unstackable;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Club extends Arsenal {
    public Club(String name, int damage, int durability) {
        super(name, damage, durability);
        try {
            this.img = ImageIO.read(new File("ProjectTheSurvivalist/res/Items/Equipments/club.png")); 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
