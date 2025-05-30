package Objek.Items.StackableItem.Foods;

public class Guava extends Food {
    public Guava(int currentStack) {
        super("Guava", currentStack);
        try {
            this.img = javax.imageio.ImageIO.read(new java.io.File("ProjectTheSurvivalist/res/Items/Foods/guava.png")); 
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
    
}
