package Objek.Items.Unstackable.Armor.Helmet;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class MetalHelmet extends Helmet {

    public MetalHelmet() {
        super("Metal Helmet", 80, 2);
        try {
            this.img = ImageIO.read(new File("ProjectTheSurvivalist/res/Items/Armor/metalhelmet2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
