package Objek.Items.StackableItem.Materials;

public class Stone extends Material {
    public Stone(int currentStack) {
        super("Stone", currentStack);
        try {
            this.img = javax.imageio.ImageIO.read(new java.io.File("ProjectTheSurvivalist/res/Items/Materials/stone.png")); 
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
