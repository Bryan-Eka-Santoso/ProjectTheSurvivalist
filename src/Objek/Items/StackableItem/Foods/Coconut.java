package Objek.Items.StackableItem.Foods;

public class Coconut extends Food {
    public Coconut(int currentStack) {
        super("Coconut", 10, currentStack);
        try {
            this.img = javax.imageio.ImageIO.read(new java.io.File("ProjectTheSurvivalist/res/Items/Foods/coconut.png")); 
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
