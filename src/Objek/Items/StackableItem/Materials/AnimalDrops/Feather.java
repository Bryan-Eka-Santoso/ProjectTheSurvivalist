package Objek.Items.StackableItem.Materials.AnimalDrops;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Objek.Items.StackableItem.Materials.Material;

public class Feather extends Material {
    public Feather(int currentStack) {
        super("Feather", currentStack);
        this.isCanSell = true;
        this.price = 8;
        try {
            this.img = ImageIO.read(new File("ProjectTheSurvivalist/res/Items/Material/feather.png")); 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
