package Objek.Items.StackableItem.Materials.Fuels;

import java.io.IOException;
import javax.imageio.ImageIO;

public class Wood extends Fuel {
    
    public Wood(int currentStack) {
        super("Wood", currentStack);
        try {
            this.img = ImageIO.read(getClass().getResource("/res/Items/Material/wood-log.png")); 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
