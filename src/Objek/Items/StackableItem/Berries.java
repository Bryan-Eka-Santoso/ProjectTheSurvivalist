package Objek.Items.StackableItem;

public class Berries extends Material {
    public Berries(int currentStack) {
        super("Berries", 20, currentStack);
        try {
            this.img = javax.imageio.ImageIO.read(new java.io.File("ProjectTheSurvivalist/res/Items/Material/berries.png")); 
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
