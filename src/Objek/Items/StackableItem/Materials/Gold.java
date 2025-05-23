package Objek.Items.StackableItem.Materials;

public class Gold extends Material {
    public Gold(int currentStack) {
        super("Gold", 20, currentStack);
        try {
            this.img = javax.imageio.ImageIO.read(new java.io.File("ProjectTheSurvivalist/res/Items/Material/gold.png")); 
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
