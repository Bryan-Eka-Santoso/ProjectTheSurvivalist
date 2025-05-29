package Objek.Items.StackableItem;

import Objek.Items.Unstackable.Unstackable;

public class Waterbucket extends Unstackable {
    public Waterbucket(int currentStack) {
        super("Water Bucket");
        try {
            this.img = javax.imageio.ImageIO.read(new java.io.File("ProjectTheSurvivalist/res/Items/WaterBucket/waterbucket.png"));
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
