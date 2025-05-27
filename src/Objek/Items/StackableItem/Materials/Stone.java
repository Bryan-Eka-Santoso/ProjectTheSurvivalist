package Objek.Items.StackableItem.Materials;

import java.io.File;

import javax.imageio.ImageIO;

public class Stone extends Material {
    public Stone(int currentStack) {
        super("Stone", currentStack);
        try {
            this.img = ImageIO.read(new File("ProjectTheSurvivalist/res/Items/Material/metalsheet.png")); 
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
