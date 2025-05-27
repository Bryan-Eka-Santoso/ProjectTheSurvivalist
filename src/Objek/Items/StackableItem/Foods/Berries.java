package Objek.Items.StackableItem.Foods;

import Objek.Items.StackableItem.Materials.Material;

public class Berries extends Material {
    public Berries(String name, int currentStack) {
        super(name, 20, currentStack);
        try {
            this.img = javax.imageio.ImageIO.read(new java.io.File("ProjectTheSurvivalist/res/Items/Material/berries.png")); 
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    public static Berries createBerries(int type, int currentStack) {
        switch (type) {
            case 1:
                return new BlackBerries(currentStack);
            case 2:
                return new BlueBerries(currentStack);
            case 3:
                return new RedBerries(currentStack);
            default:
                throw new IllegalArgumentException("Unknown berry type: " + type);
        }
    }
}
