package Objek.Items.StackableItem.Materials;

public class MetalFrame extends Material {
    public MetalFrame(int currentStack) {
        super("Metal Frame", 20, currentStack);
        try {
            this.img = javax.imageio.ImageIO.read(new java.io.File("ProjectTheSurvivalist/res/Items/Material/metalframe.png")); 
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
    
}
