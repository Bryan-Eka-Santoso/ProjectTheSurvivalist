package Objek.Items.StackableItem.Foods;

public class BlueBerries extends Berries {
    public BlueBerries(int currentStack) {
        super("Blue Berries", currentStack);
        try {
            this.img = javax.imageio.ImageIO.read(new java.io.File("ProjectTheSurvivalist/res/Items/Food/blueberries.png")); 
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
