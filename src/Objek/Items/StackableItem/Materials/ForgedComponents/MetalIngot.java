package Objek.Items.StackableItem.Materials.ForgedComponents;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Objek.Items.StackableItem.Materials.Material;

public class MetalIngot extends Material {
    public MetalIngot(int currentStack) {
        super("Metal Ingot", currentStack);
        try {
            this.img = ImageIO.read(new File("ProjectTheSurvivalist/res/Items/Material/metalingot.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
