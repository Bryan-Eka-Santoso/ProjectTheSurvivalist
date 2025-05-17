package Objek.Items.StackableItem.Seeds;

import Objek.Items.StackableItem.Stackable;

public class Seeds extends Stackable {
    public Seeds(String name, int currentStack) {
        super(name, 30, currentStack);
        try {
            this.img = javax.imageio.ImageIO.read(new java.io.File("ProjectTheSurvivalist/res/Items/Seeds/" + name.toLowerCase().replace(" ", "") + ".png"));
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
