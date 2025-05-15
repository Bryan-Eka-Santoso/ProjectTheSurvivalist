package Objek.Items.StackableItem;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Feather extends Material {
    public Feather(int currentStack) {
        super("Feather", 20, currentStack);
        try {
            this.img = ImageIO.read(new File("ProjectTheSurvivalist/res/Items/Material/feather.png")); 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
