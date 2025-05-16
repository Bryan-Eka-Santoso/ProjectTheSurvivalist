package Objek.Items.StackableItem;

public class SwordHandle extends Material {
    public SwordHandle(int currentStack) {
        super("Sword Handle", 20, currentStack);
        try {
            this.img = javax.imageio.ImageIO.read(new java.io.File("ProjectTheSurvivalist/res/Items/Material/swordhandle.png")); 
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
    
}
