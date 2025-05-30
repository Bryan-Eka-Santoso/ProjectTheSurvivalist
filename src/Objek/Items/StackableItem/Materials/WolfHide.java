package Objek.Items.StackableItem.Materials;

public class WolfHide extends Material {
    public WolfHide(int currentStack) {
        super("Wolf Hide", currentStack);
        try {
            this.img = javax.imageio.ImageIO.read(new java.io.File("ProjectTheSurvivalist/res/Items/Material/wolfHide.png"));
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
