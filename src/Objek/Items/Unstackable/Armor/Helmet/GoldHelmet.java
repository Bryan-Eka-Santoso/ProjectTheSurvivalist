package Objek.Items.Unstackable.Armor.Helmet;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class GoldHelmet extends Helmet {

    public GoldHelmet() {
        super("Gold Helmet", 50, 2);
        try {
            this.img = ImageIO.read(new File("ProjectTheSurvivalist/res/Items/Armor/goldhelmet2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
