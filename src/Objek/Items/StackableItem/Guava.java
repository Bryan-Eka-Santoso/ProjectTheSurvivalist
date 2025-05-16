package Objek.Items.StackableItem;

public class Guava extends Food {
    public Guava(int currentStack) {
        super("Guava", 10, currentStack);
        try {
            this.img = javax.imageio.ImageIO.read(new java.io.File("ProjectTheSurvivalist/res/Items/Foods/guava.png")); 
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
    
}
