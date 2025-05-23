package Objek.Items.StackableItem.Materials;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class MetalIngot extends Material {
    public MetalIngot(int currentStack) {
        super("Metal Ingot", 20, currentStack);
        try {
            this.img = ImageIO.read(new File("ProjectTheSurvivalist/res/Items/Material/metalingot.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
