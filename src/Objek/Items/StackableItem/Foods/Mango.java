package Objek.Items.StackableItem.Foods;

public class Mango extends Food {
    public Mango(int currentStack) {
        super("Mango", 10, currentStack);
        try {
            this.img = javax.imageio.ImageIO.read(new java.io.File("ProjectTheSurvivalist/res/Items/Foods/mango.png")); 
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
    
}
