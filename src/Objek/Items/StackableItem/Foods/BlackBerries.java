package Objek.Items.StackableItem.Foods;

public class Blackberries extends Berries {
    public Blackberries(int currentStack) {
        super("Blackberries", currentStack);
        try {
            this.img = javax.imageio.ImageIO.read(new java.io.File("ProjectTheSurvivalist/res/Items/Foods/blackberry.png")); 
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
