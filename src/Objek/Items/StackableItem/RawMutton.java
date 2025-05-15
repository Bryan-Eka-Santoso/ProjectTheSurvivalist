package Objek.Items.StackableItem;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class RawMutton extends Food {
    public RawMutton(int currentStack) {
        super("Raw Mutton", 10, currentStack);
            try {
                this.img = ImageIO.read(new File("ProjectTheSurvivalist/res/Items/Foods/null.png")); 
            } catch (IOException e) {
                e.printStackTrace();
            } 
    }
}
