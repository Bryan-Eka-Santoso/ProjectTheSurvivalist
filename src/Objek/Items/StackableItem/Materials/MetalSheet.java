package Objek.Items.StackableItem.Materials;

public class MetalSheet extends Material {
    public MetalSheet(int currentStack) {
        super("Metal Sheet", 20, currentStack);
        try {
            this.img = javax.imageio.ImageIO.read(new java.io.File("ProjectTheSurvivalist/res/Items/Material/metalsheet.png")); 
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
    
}
