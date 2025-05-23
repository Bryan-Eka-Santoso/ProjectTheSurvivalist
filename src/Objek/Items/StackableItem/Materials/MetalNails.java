package Objek.Items.StackableItem.Materials;

public class MetalNails extends Material {
    public MetalNails(int currentStack) {
        super("Metal Nails", 5, currentStack);
        try {
            this.img = javax.imageio.ImageIO.read(new java.io.File("ProjectTheSurvivalist/res/Items/Material/metalnails.png"));
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
