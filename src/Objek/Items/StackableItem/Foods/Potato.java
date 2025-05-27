package Objek.Items.StackableItem.Foods;

public class Potato extends Food {
    public Potato(int currentStack) {
        super("Potato", 10, currentStack);
        try {
            this.img = javax.imageio.ImageIO.read(new java.io.File("ProjectTheSurvivalist/res/Items/Foods/potato.png")); 
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
    
}
