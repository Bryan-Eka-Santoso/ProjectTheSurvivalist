package Object.Items.StackableItem;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class RawMeat extends Food {
    public RawMeat(int currentStack) {
        super("Raw Meat", 10, currentStack);
            try {
                this.img = ImageIO.read(new File("ProjectTheSurvivalist/res/Items/Foods/null.png")); 
            } catch (IOException e) {
                e.printStackTrace();
            } 
    }
}
