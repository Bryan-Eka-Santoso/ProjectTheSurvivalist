package Objek.Items.StackableItem.Foods;

public class BlackBerries extends Berries {
    public BlackBerries(int currentStack) {
        super("Black Berries", currentStack);
        try {
            this.img = javax.imageio.ImageIO.read(new java.io.File("ProjectTheSurvivalist/res/Items/Food/blackberries.png")); 
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
