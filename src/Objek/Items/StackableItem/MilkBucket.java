package Objek.Items.StackableItem;

import Objek.Items.Unstackable.Unstackable;

public class MilkBucket extends Unstackable {
    public MilkBucket(int currentStack) {
        super("Milk Bucket");
        try {
            this.img = javax.imageio.ImageIO.read(new java.io.File("ProjectTheSurvivalist/res/Items/MilkBucket/milkbucket.png"));
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
