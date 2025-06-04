package Objek.Items.StackableItem.Foods.Fruits;

public class RaspBerries extends Berries {
    public RaspBerries(int currentStack) {
        super("Raspberries", currentStack);
        try {
            this.img = javax.imageio.ImageIO.read(new java.io.File("ProjectTheSurvivalist/res/Items/Foods/raspberry.png")); 
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }    
}
