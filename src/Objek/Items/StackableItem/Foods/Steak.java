package Objek.Items.StackableItem.Foods;

public class Steak extends Food {
    public Steak(int currentStack) {
        super("Steak", 20, currentStack);
        try {
            this.img = javax.imageio.ImageIO.read(new java.io.File("ProjectTheSurvivalist/res/Items/Food/steak.png")); 
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
