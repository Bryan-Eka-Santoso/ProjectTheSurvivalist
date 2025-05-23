package Objek.Items.StackableItem.Materials;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Wheat extends Material {
    public Wheat(int currentStack) {
        super("Wheat", 20, currentStack);
        try {
            this.img = ImageIO.read(new File("ProjectTheSurvivalist/res/Items/Material/wheat.png")); 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
