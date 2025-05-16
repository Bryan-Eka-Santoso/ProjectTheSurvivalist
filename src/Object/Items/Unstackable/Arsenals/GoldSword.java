package Object.Items.Unstackable.Arsenals;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class GoldSword extends Sword {
    public GoldSword() {
        super("Gold Sword", 30, 50);
        try {
            this.img = ImageIO.read(new File("ProjectTheSurvivalist/res/Items/Equipments/goldsword.png")); 
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }
}
