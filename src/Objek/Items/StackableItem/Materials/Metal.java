package Objek.Items.StackableItem.Materials;

public class Metal extends Material {
    public Metal(int currentStack) {
        super("Metal", 20, currentStack);
        try {
            this.img = javax.imageio.ImageIO.read(new java.io.File("ProjectTheSurvivalist/res/Items/Material/metal.png")); 
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

}
