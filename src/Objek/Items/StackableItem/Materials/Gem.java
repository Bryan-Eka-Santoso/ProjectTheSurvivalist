package Objek.Items.StackableItem.Materials;

public class Gem extends Material {
    public Gem(int currentStack) {
        super("Gem", 20, currentStack);
        try {
            this.img = javax.imageio.ImageIO.read(new java.io.File("ProjectTheSurvivalist/res/Items/Material/gem.png")); 
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
