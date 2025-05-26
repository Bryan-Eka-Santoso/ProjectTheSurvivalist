package Objek.Items.Unstackable.Armor.Helmet;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class IronHelmet extends Helmet {

    public IronHelmet() {
        super("Iron Helmet", 100, 20, 100);
        try {
            this.img = ImageIO.read(new File("ProjectTheSurvivalist/res/Items/Armor/ironhelmet.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
