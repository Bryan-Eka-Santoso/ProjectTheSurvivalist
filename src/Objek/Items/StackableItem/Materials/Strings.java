package Objek.Items.StackableItem.Materials;

public class Strings extends Material {
    
    public Strings(int currentStack) {
        super("String", currentStack);
        try {
            this.img = javax.imageio.ImageIO.read(getClass().getResource("/res/Items/Material/string.png"));
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
