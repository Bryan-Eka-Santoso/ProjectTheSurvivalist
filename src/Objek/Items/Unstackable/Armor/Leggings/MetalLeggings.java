package Objek.Items.Unstackable.Armor.Leggings;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class MetalLeggings extends Leggings {
    public MetalLeggings() {
        super("Metal Leggings", 90, 2);
        try {
            this.img = ImageIO.read(new File("ProjectTheSurvivalist/res/Items/Armor/metalpants.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
