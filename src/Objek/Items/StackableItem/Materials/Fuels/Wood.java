package Objek.Items.StackableItem.Materials.Fuels;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Wood extends Fuel {
    public Wood(int currentStack) {
        super("Wood", 20, currentStack);
        try {
            this.img = ImageIO.read(new File("ProjectTheSurvivalist/res/Items/Material/wood-log.png")); 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
