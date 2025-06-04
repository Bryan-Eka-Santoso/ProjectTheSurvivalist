package Objek.Items.StackableItem.Materials.Fuels;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Objek.Items.StackableItem.Materials.Material;

public abstract class Fuel extends Material {
    public Fuel(String name, int currentStack) {
        super(name, currentStack);
        try {
            this.img = ImageIO.read(new File("ProjectTheSurvivalist/res/Items/Material/wood-log.png")); 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
