package Objek.Items.StackableItem.Foods;

public class RedBerries extends Berries {
    public RedBerries(int currentStack) {
        super("Red Berries", currentStack);
        try {
            this.img = javax.imageio.ImageIO.read(new java.io.File("ProjectTheSurvivalist/res/Items/Food/redberries.png")); 
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }    
}
