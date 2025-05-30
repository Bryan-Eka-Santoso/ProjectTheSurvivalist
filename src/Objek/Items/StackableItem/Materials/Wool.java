package Objek.Items.StackableItem.Materials;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Wool extends Material {
    public Wool(int currentStack) {
        super("Wool", currentStack);
            try {
                this.img = ImageIO.read(new File("ProjectTheSurvivalist/res/Items/Material/wool.png")); 
            } catch (IOException e) {
                e.printStackTrace();
            } 
    }
}
