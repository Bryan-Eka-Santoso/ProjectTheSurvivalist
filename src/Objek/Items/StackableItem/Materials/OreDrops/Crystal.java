package Objek.Items.StackableItem.Materials.OreDrops;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Objek.Items.StackableItem.Materials.Material;

public class Crystal extends Material {
    public Crystal(int currentStack) {
        super("Crystal", currentStack);
        try {
            this.img = ImageIO.read(new File("ProjectTheSurvivalist/res/Items/Material/crystal.png")); 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
