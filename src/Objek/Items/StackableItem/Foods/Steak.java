package Objek.Items.StackableItem.Foods;

public class Steak extends Food {
    public Steak(int currentStack) {
        super("Steak", currentStack);
        try {
            this.img = javax.imageio.ImageIO.read(new java.io.File("ProjectTheSurvivalist/res/Items/Foods/steak.png")); 
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
