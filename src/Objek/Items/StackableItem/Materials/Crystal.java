package Objek.Items.StackableItem.Materials;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Crystal extends Material {
    public Crystal(int currentStack) {
        super("Crystal", 20, currentStack);
        try {
            this.img = ImageIO.read(new File("ProjectTheSurvivalist/res/Items/Material/crystal.png")); 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
