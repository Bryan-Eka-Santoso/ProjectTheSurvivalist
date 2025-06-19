package Objek.Items.StackableItem.Materials;

import java.io.IOException;
import javax.imageio.ImageIO;

public class Wheat extends Material {
    
    public Wheat(int currentStack) {
        super("Wheat", currentStack);
        try {
            this.img = ImageIO.read(getClass().getResource("/res/Items/Material/wheat.png")); 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
