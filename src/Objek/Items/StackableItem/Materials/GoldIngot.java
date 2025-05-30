package Objek.Items.StackableItem.Materials;

public class GoldIngot extends Material {
    public GoldIngot(int currentStack) {
        super("Gold Ingot" , currentStack);
        try {
            this.img = javax.imageio.ImageIO.read(new java.io.File("ProjectTheSurvivalist/res/Items/Material/goldingot.png")); 
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
    
}
